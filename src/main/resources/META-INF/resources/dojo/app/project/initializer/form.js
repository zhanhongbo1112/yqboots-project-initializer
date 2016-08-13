define(['app/_base/PageRegistry'],
    function (PageRegistry) {
        return {
            startup: function () {
                PageRegistry.appendCss(PageRegistry.CSS_FORMS);
            }
        };
    });