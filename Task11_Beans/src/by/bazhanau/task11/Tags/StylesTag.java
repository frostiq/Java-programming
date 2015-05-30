package by.bazhanau.task11.Tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class StylesTag extends BodyTagSupport {
    @Override
    public int doStartTag() throws JspException {
        return EVAL_BODY_BUFFERED;
    }

    @Override
    public int doAfterBody() throws JspException {
        String[] styles = getBodyContent().getString().split(";");
        BundleTag tag = (BundleTag)findAncestorWithClass(this, BundleTag.class);
        tag.setStyles(styles);
        return SKIP_BODY;
    }
}