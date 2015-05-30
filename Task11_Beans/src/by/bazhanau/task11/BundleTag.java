package by.bazhanau.task11;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class BundleTag extends TagSupport {
    private String[] scripts;
    private String[] styles;
    private String scriptFormat = "<script type=\"text/javascript\" src=\"/content/%s\"></script>";
    private String styleFormat = "<link rel=\"stylesheet\" type=\"text/css\" href=\"/content/%s\">";

    public void setScripts(String str){
        scripts = str.split(";");
    }

    public void setStyles(String str){
        styles = str.split(";");
    }

    @Override
    public int doEndTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            for (String script : scripts){
                out.println(String.format(scriptFormat, script));
            }
            for (String style : styles){
                out.println(String.format(styleFormat, style));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }
}
