var App = (function (App, $) {

    /**
     * App.init()
     */
    App.init = function () {
        App.setTimeZoneCookie();
        window.baseUrl = "http://vocabspa.appspot.com/";
    };

    App.setTimeZoneCookie = function () {
        var timeZoneCookie = "timezoneoffset";

        //if time zone cookie does not exist create one and reload page
        if (!$.cookie(timeZoneCookie)) {
            $.cookie(timeZoneCookie, new Date().getTimezoneOffset());
            location.reload()
        }
        //if user time zone has changed, overwrite time zone cookie and reload page
        else {
            var storedOffset = parseInt($.cookie(timeZoneCookie));
            var currentOffset = new Date().getTimezoneOffset();
            if (storedOffset != currentOffset) {
                $.cookie(timeZoneCookie, new Date().getTimezoneOffset());
                location.reload()
            }
        }
    };

    App.displayAdd = function() {
        var url = baseUrl + "add.html";
        $.ajax(url).done(function(data) {
            $('.takeAction').html(data);
        });
    };

    App.displayDicts = function() {
        $('.takeAction').html('<input type="button" value="Build up my vocabulary" onclick="App.displayAdd()">');
    };

    App.addEntry = function() {
        var entry = new Object();
        entry.frn = $('form input[name=frn]').val();
        entry.prn = $('form input[name=prn]').val();
        entry.ntv = $('form input[name=ntv]').val();

        var json = JSON.stringify(entry);
        var url = baseUrl + "res/add";
        $.post(url, json).done(function(data) {
            $('#saveActionOutcome').html(data);
        });
    };

    return App;
}(App || {}, jQuery));
