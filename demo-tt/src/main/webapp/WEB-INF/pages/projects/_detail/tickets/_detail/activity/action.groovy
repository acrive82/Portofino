import com.manydesigns.portofino.tt.TtUtils

import com.manydesigns.elements.ElementsThreadLocals
import com.manydesigns.elements.messages.SessionMessages
import com.manydesigns.portofino.buttons.GuardType
import com.manydesigns.portofino.buttons.annotations.Button
import com.manydesigns.portofino.buttons.annotations.Guard
import com.manydesigns.portofino.di.Inject
import com.manydesigns.portofino.modules.DatabaseModule
import com.manydesigns.portofino.pageactions.custom.CustomAction
import com.manydesigns.portofino.persistence.Persistence
import com.manydesigns.portofino.security.AccessLevel
import com.manydesigns.portofino.security.RequiresPermissions
import com.manydesigns.portofino.tt.ActivityItem
import com.manydesigns.portofino.tt.ActivityItem.Arg
import org.apache.shiro.SecurityUtils
import org.hibernate.Session
import org.hibernate.criterion.Order
import org.hibernate.criterion.Restrictions
import net.sourceforge.stripes.action.*

@RequiresPermissions(level = AccessLevel.VIEW)
class TicketsActivityAction extends CustomAction {

    Serializable project;
    Serializable ticket;

    String comment

    @Before
    public void prepareProject() {
        project = ElementsThreadLocals.getOgnlContext().get("project");
        ticket = ElementsThreadLocals.getOgnlContext().get("ticket");
    }

    @Inject(DatabaseModule.PERSISTENCE)
    private Persistence persistence;

    List<ActivityItem> activityItems = new ArrayList<ActivityItem>();

    //**************************************************************************
    // Role checking
    //**************************************************************************

    public boolean isContributor() {
        return TtUtils.principalHasProjectRole(project, TtUtils.ROLE_CONTRIBUTOR);
    }

    //**************************************************************************
    // View
    //**************************************************************************

    @DefaultHandler
    public Resolution execute() {
        Session session = persistence.getSession("tt");
        List items = session.createCriteria("activity")
                .add(Restrictions.eq("project", ticket.project))
                .add(Restrictions.eq("n", ticket.n))
                .addOrder(Order.asc("id"))
                .list();

        Locale locale = context.request.locale;
        for (Object item : items) {
            String userName = "$item.fk_activity_user.first_name $item.fk_activity_user.last_name"

            Date timestamp = item.date;
            String imageSrc = "/images/user-placeholder-40x40.png";
            String imageHref = null;
            String imageAlt = userName;
            String message = item.message;
            String key = "ticket." + item.fk_activity_type.type
            ActivityItem activityItem = new ActivityItem(
                    locale,
                    timestamp,
                    imageSrc,
                    imageHref,
                    imageAlt,
                    message,
                    key,
                    new Arg(userName, null),
            );
            activityItems.add(activityItem)
        }



        return new ForwardResolution("/jsp/projects/tickets/activity.jsp");
    }

    @Button(list = "activity", key = "post.comment")
    @Guard(test="isContributor()", type=GuardType.VISIBLE)
    public Resolution postComment() {
        Session session = persistence.getSession("tt");
        Object principal = SecurityUtils.subject.principal;
        Date now = new Date();
        TtUtils.addActivity(session, ticket, principal.id, now, TtUtils.ACTIVITY_TYPE_COMMENT_CREATED, comment);
        session.getTransaction().commit();
        SessionMessages.addInfoMessage("Comment posted successfully");
        return new RedirectResolution("/projects/$ticket.project/tickets/$ticket.project/$ticket.n")
    }

}