package by.bazhanau.task11.Tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class ScriptsTag extends BodyTagSupport {
    @Override
    public int doStartTag() throws JspException {
        return EVAL_BODY_BUFFERED;
    }

    @Override
    public int doAfterBody() throws JspException {
        String[] scripts = getBodyContent().getString().split(";");
        BundleTag tag = (BundleTag)findAncestorWithClass(this, BundleTag.class);
        tag.setScripts(scripts);
        return SKIP_BODY;
    }
}
