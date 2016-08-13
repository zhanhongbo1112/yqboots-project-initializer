define(['jquery'], function () {
    // check whether the css files are merged into application.css
    var cssMerged = false;

    // register the pages and css, based on the template
    return {
        CSS_ERROR: ["/theme/css/pages/error.css"],
        CSS_FORMS: ["/dojo/jquery/skyforms/css/sky-forms.css", "/dojo/jquery/skyforms/custom/custom-sky-forms.css"],

        appendCss: function (cssFiles) {
            if (cssMerged) {
                return;
            }
            $.each(cssFiles, function (i, cssFile) {
                $("head").append("<link>");
                var css = $("head").children(":last");
                css.attr({
                    rel: "stylesheet",
                    type: "text/css",
                    href: cssFile
                });
            });
        }
    };
});