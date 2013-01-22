/*
* Copyright (C) 2005-2013 ManyDesigns srl.  All rights reserved.
* http://www.manydesigns.com/
*
* Unless you have purchased a commercial license agreement from ManyDesigns srl,
* the following license terms apply:
*
* This program is free software; you can redistribute it and/or modify
* it under the terms of the GNU General Public License version 3 as published by
* the Free Software Foundation.
*
* There are special exceptions to the terms and conditions of the GPL
* as it is applied to this software. View the full text of the
* exception in file OPEN-SOURCE-LICENSE.txt in the directory of this
* software distribution.
*
* This program is distributed WITHOUT ANY WARRANTY; and without the
* implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
* See the GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, see http://www.gnu.org/licenses/gpl.txt
* or write to:
* Free Software Foundation, Inc.,
* 59 Temple Place - Suite 330,
* Boston, MA  02111-1307  USA
*
*/

package com.manydesigns.portofino.interceptors;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.manydesigns.portofino.actions.user.LoginAction;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.ExecutionContext;
import net.sourceforge.stripes.controller.Interceptor;
import net.sourceforge.stripes.controller.Intercepts;
import net.sourceforge.stripes.controller.LifecycleStage;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Paolo Predonzani     - paolo.predonzani@manydesigns.com
 * @author Angelo Lupo          - angelo.lupo@manydesigns.com
 * @author Giampiero Granatella - giampiero.granatella@manydesigns.com
 * @author Alessio Stalla       - alessio.stalla@manydesigns.com
 */
@Intercepts(LifecycleStage.EventHandling)
public class GAELogInOutInterceptor implements Interceptor {
    public static final String copyright =
            "Copyright (c) 2005-2013, ManyDesigns srl";

    public static final Logger logger = LoggerFactory.getLogger(GAELogInOutInterceptor.class);

    public Resolution intercept(ExecutionContext context) throws Exception {
        if(context.getActionBean() instanceof LoginAction) {
            if(context.getHandler().getName().equals("execute")) {
                //((LoginAction) context.getActionBean()).getApplication().getAppConfiguration();
                Subject subject = SecurityUtils.getSubject();
                if (!subject.isAuthenticated()) {
                    logger.debug("User not authenticated, redirecting to GAE login URL");
                    HttpServletRequest request = context.getActionBeanContext().getRequest();
                    UserService userService = UserServiceFactory.getUserService();
                    String loginUrl = userService.createLoginURL(request.getParameter("returnUrl"));
                    return new RedirectResolution(loginUrl);
                }
            } else if(context.getHandler().getName().equals("logout")) {
                Subject subject = SecurityUtils.getSubject();
                if(subject.isAuthenticated()) {
                    logger.debug("User not authenticated, redirecting to GAE logout URL");
                    UserService userService = UserServiceFactory.getUserService();
                    ((LoginAction) context.getActionBean()).logout();
                    String logoutUrl = userService.createLogoutURL(
                            context.getActionBeanContext().getRequest().getContextPath() + "/");
                    return new RedirectResolution(logoutUrl);
                }
            }
        }
        return context.proceed();
    }
}