var Vocapp = (function (Vocapp, $) {

    /**
     * Vocapp.init()
     */
    Vocapp.init = function () {
        Vocapp.setTimeZoneCookie();
        window.baseUrl = "http://vocabspa.appspot.com/";
        $.extend( $.fn.dataTable.defaults, {
            ordering: false,
            dom: 't',
            scrollY: 400,
            scrollCollapse: true
        } );
    };

    Vocapp.setTimeZoneCookie = function () {
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

    Vocapp.displayAdd = function() {
        var url = baseUrl + "add.html";
        $.ajax(url).done(function(data) {
            $('.takeAction').html(data);
        });
        window.user = $('#userNickname').text();
        window.dict = $('#dictionaryName').text();
    };

    Vocapp.displayDicts = function() {
        $('.takeAction').html('<input type="button" value="Build up my vocabulary" onclick="Vocapp.displayAdd()">');
    };

    Vocapp.displayAddOns = function() {
        $('form[name=basicsForm] :input').prop('disabled', true);
        $('#addOns').css('display', 'block');
        $('form[name=addOnsForm] input[type=text]').val("");
        $('input[name=frnL]').prop('disabled', false).focus();
        $('input[name=prnL], input[name=ntvL]').prop('disabled', true);
        $('#lookupActionOutcome').html('');
    };


    Vocapp.hideAddOns = function() {
        $('form[name=basicsForm] :input').prop('disabled', false);
        $('#addOns').css('display', 'none');
    };

    Vocapp.toggleTextInputs = function(inputContainer) {
        $('form[name=addOnsForm] input[type=text]').prop('disabled', true);
        $(inputContainer).children().first().prop('disabled', false).focus();
    };

    Vocapp.addEntry = function() {
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

    Vocapp.lookUp = function() {
        var lookUpInput = $('form[name=addOnsForm] input[type=text]:enabled');
        var field = lookUpInput.attr('name').substring(0, 3);
        var lookUpString = lookUpInput.val();
        if (lookUpString == '') {
            alert("What am I looking up?");
        } else {
            $('#lookupActionOutcome').html('<table cellpadding="0" cellspacing="0" border="0" class="display" id="lookupDataTable"></table>')
            var url = baseUrl + "res/entry/lookup?user=" + window.user + "&dict=" + window.dict + "&field=" + field + "&string=" + lookUpString;
            $.get(url).done(function(data) {
                window.oAuxTable = $('#lookupDataTable').DataTable( {
                    data: data,
                    columns: [
                        {
                            data: 'frn',
                            name: 'frn',
                            title: 'Foreign'
                        },
                        {
                            data: 'prn',
                            name: 'prn',
                            title: 'Pronunciation'
                        },
                        {
                            data: 'ntv',
                            name: 'ntv',
                            title: 'Native'
                        }
                    ],
                    initComplete: function(settings, json) {
                        $('#lookupDataTable tbody').on('click', 'tr', function() {
                            $(this).toggleClass('selected');
                        });
                        //Enable Ctrl+S event
                        $(document).keydown(function(e) {
                            if(e.keyCode == 83 && e.ctrlKey) {
                                e.preventDefault();
                                var selectedEntities = $('#lookupDataTable').find('tr.selected');
                                console.log(selectedEntities);
                                var synonymDiv;
                                for(var i = 0; i < selectedEntities.length; i++) {
                                    synonymDiv = $('<div class="addonSyns"></div>').html(selectedEntities[i].children[0].innerHTML);
                                    $('#addonSynonyms').append(synonymDiv);
                                }
                            }
                        });
                    }
                } );
            });
        }
    };

    Vocapp.handleSelectRow = function() {
        var id = this.id;
        var index = $.inArray(id, selected);

        if ( index === -1 ) {
            selected.push( id );
        } else {
            selected.splice( index, 1 );
        }

        $(this).toggleClass('selected');
        console.log(selected);
    };

    return Vocapp;
}(Vocapp || {}, jQuery));
