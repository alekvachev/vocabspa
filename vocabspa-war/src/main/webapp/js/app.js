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
        window.user = $('#userNickname').text();
        window.dict = $('#dictionaryName').text();
    };

    App.displayDicts = function() {
        $('.takeAction').html('<input type="button" value="Build up my vocabulary" onclick="App.displayAdd()">');
    };

    App.displayAddOns = function() {
        $('form[name=basicsForm] :input').prop('disabled', true);
        $('#addOns').css('display', 'block');
        $('form[name=addOnsForm] input[type=text]').val("");
        $('input[name=frnL]').prop('disabled', false);
        $('input[name=frnL]').focus();
        $('input[name=prnL], input[name=ntvL]').prop('disabled', true);
    };

    App.hideAddOns = function() {
        $('form[name=basicsForm] :input').prop('disabled', false);
        $('#addOns').css('display', 'none');
    };

    App.toggleTextInputs = function(inputContainer) {
        $('form[name=addOnsForm] input[type=text]').prop('disabled', true);
        $(inputContainer).children().first().prop('disabled', false).focus();
    };

    App.addEntry = function() {
        $('.txtInput span').css('display', 'none');
        if ($('form input[name=frn]').val() == "") {
            $('#frnInput span').css('display', 'inline');
        } else if ($('form input[name=ntv]').val() == "") {
            $('#ntvInput span').css('display', 'inline');
        } else {
            var entry = new Object();
            entry.frn = $('form input[name=frn]').val();
            entry.prn = $('form input[name=prn]').val();
            entry.ntv = $('form input[name=ntv]').val();
            entry.dictionary = window.dict;
            entry.userEmail = window.user;
            entry.timezoneOffset = $('#timezoneOffset').text();

            var json = JSON.stringify(entry);
            var url = baseUrl + "res/entry/save";
            $.ajax({
                url: url,
                type: 'post',
                data: json,
                contentType: 'application/json'
            }).done(function(data) {
                $('#saveActionOutcome').text(JSON.stringify(data));
                console.log(data);
            });
        }
    };

    App.lookUp = function() {
        var lookUpInput = $('form[name=addOnsForm] input[type=text]:enabled');
        var field = lookUpInput.attr('name').substring(0, 3);
        var lookUpString = lookUpInput.val();
        var url = baseUrl + "res/entry/lookup?user=" + window.user + "&dict=" + window.dict + "&field=" + field + "&string=" + lookUpString;
        $.get(url).done(function(data) {
            $('#lookupActionOutcome').text(JSON.stringify(data));
        });
    };

    return App;
}(App || {}, jQuery));
