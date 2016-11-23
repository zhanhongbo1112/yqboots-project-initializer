define(['dojo/_base/lang'], function (lang) {
    return {
        startup: function () {
            $('#cancel').click(lang.hitch(this, '_onCancel'));
        },

        _onCancel: function (e) {
            e.preventDefault();
            window.history.back();
        }
    };
});