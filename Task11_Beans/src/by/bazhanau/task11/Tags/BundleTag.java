package by.bazhanau.task11.Tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class BundleTag extends TagSupport {
    private String[] scripts;
    private String[] styles;
    private String root;
    private String scriptFormat = "<script type=\"text/javascript\" src=\"/%s/%s\"></script>";
    private String styleFormat = "<link rel=\"stylesheet\" type=\"text/css\" href=\"/%s/%s\">";

    public void setRoot(String root) {
        this.root = root;
    }

    public void setScripts(String[] scripts) {
        this.scripts = scripts;
    }

    public void setStyles(String[] styles) {
        this.styles = styles;
    }

    @Override
    public int doStartTag() throws JspException {
        return EVAL_BODY_INCLUDE;
    }

    @Override
    public int doEndTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            for (String script : scripts){
                out.println(String.format(scriptFormat, root, script));
            }
            for (String style : styles){
                out.println(String.format(styleFormat, root, style));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EVAL_PAGE;
    }
}
