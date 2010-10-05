/*
 * Copyright (C) 2005-2010 ManyDesigns srl.  All rights reserved.
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

package com.manydesigns.elements.json;

import com.manydesigns.elements.logging.LogUtil;
import com.manydesigns.elements.util.Util;
import org.apache.commons.lang.StringEscapeUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.text.MessageFormat;
import java.util.logging.Logger;

/*
* @author Paolo Predonzani     - paolo.predonzani@manydesigns.com
* @author Angelo Lupo          - angelo.lupo@manydesigns.com
* @author Giampiero Granatella - giampiero.granatella@manydesigns.com
*/
public class JsonBuffer {
    public static final String copyright =
            "Copyright (c) 2005-2010, ManyDesigns srl";

    //**************************************************************************
    // Static fields
    //**************************************************************************

    public static boolean checkWellFormed = false;

    public Logger logger = LogUtil.getLogger(JsonBuffer.class);

    //**************************************************************************
    // Fields
    //**************************************************************************

    protected final Writer writer;
    protected boolean first;

    //**************************************************************************
    // Constructors
    //**************************************************************************


    /**
     * Creates a new instance of JsonBuffer
     */
    public JsonBuffer() {
        this(new StringWriter());
    }

    /**
     * Creates a new instance of JsonBuffer
     * @param writer
     */
    public JsonBuffer(Writer writer) {
        this.writer = writer;
        first = true;
    }

    //**************************************************************************
    // Public methods
    //**************************************************************************

    public void openArray() {
        try {
            writeCommaIfNeeded();
            writer.write("[");
        } catch (IOException e) {
            LogUtil.severe(logger, "Json writer exception", e);
        }
        first = true;
    }

    public void closeArray() {
        try {
            writer.write("]");
        } catch (IOException e) {
            LogUtil.severe(logger, "Json writer exception", e);
        }
        first = false;
    }

    public void openObject() {
        try {
            writeCommaIfNeeded();
            writer.write("{");
        } catch (IOException e) {
            LogUtil.severe(logger, "Json writer exception", e);
        }
        first = true;
    }

    public void closeObject() {
        try {
            writer.write("}");
        } catch (IOException e) {
            LogUtil.severe(logger, "Json writer exception", e);
        }
        first = false;
    }

    public void writeKeyValue(String key, String value) {
        String rawValue = MessageFormat.format("\"{0}\"",
                StringEscapeUtils.escapeJava(value));
        writeKeyRawValue(key, rawValue);
    }

    public void writeKeyValue(String key, Integer intValue) {
        String rawValue = Util.convertValueToString(intValue);
        writeKeyRawValue(key, rawValue);
    }

    public void writeKeyValue(String key, Boolean booleanValue) {
        String rawValue;
        if (booleanValue == null) {
            rawValue = null;
        } else if (booleanValue) {
            rawValue = "true";
        } else {
            rawValue = "false";
        }
        writeKeyRawValue(key, rawValue);
    }

    //**************************************************************************
    // Utility methods
    //**************************************************************************

    protected void writeKeyRawValue(String key, String rawValue) {
        if (rawValue == null) {
            rawValue = "null";
        }
        try {
            writeCommaIfNeeded();
            String text = MessageFormat.format("\"{0}\":{1}",
                    StringEscapeUtils.escapeJava(key),
                    rawValue);
            writer.write(text);
        } catch (IOException e) {
            LogUtil.severe(logger, "Json writer exception", e);
        }
    }

    protected void writeCommaIfNeeded() throws IOException {
        if (first) {
            first = false;
        } else {
            writer.write(",");
        }
    }
    
    public String toString() {
        return writer.toString();
    }

}