define(['dojo/_base/lang'], function (lang) {
    return {
        startup: function () {
            $('#file').change(lang.hitch(this, '_onChangeFile'));
            $('#cancel').click(lang.hitch(this, '_onCancel'));
        },

        _onChangeFile : function(e) {
            var inputFile = $(e.target);
            inputFile.parent().next().val(inputFile.val());
        },

        _onCancel: function (e) {
            e.preventDefault();
            window.history.back();
        }
    };
});