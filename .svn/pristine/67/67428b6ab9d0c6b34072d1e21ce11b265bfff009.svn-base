angular.module('inra').factory('Calendrier', function () {
    return function () {
        this.open = function ($event) {
            if (!this.opened) {
                $event.preventDefault();
                this.opened = !this.opened;
            }
            $event.stopPropagation();
        };
        this.opened = false;
        this.format = 'dd/MM/yyyy';
        this.formating = function (date) {
            if (date) {
                var time = new Date(Date.UTC(date.getFullYear(), date.getMonth(), date.getDate(), date.getHours()+1, date.getMinutes(), date.getSeconds())).toISOString();
                return time.substring(0, 19) + time.substring(23, 24);
            }
            return null;
        };
    };
}).factory('JsonParse', function () {
    return function (data) {
        try {
            var jsonObject = JSON.parse(data);
            return jsonObject;
        }
        catch (e) {
            return [];
        }
    };
}).factory('userFilter', function () {
    return function (userFilter, champ) {
        var temp = angular.copy(userFilter);
        userFilter = {};
        for (var i in champ) {
            userFilter[champ[i]] = temp[champ[i]] ? temp[champ[i]] : "";
        }
        return userFilter;
    };
}).factory('utf8Decode', [function () {
        function utf8Decode(str, i) {
            var caract = [[/%c3%bb/gi, 'û'], [/%c3%bc/gi, 'ü'], [/%c3%ae/gi, 'î'], [/%c3%af/gi, 'ï'], [/%c3%a6/gi, 'æ'], [/%c3%a0/, 'à'], [/%c3%a1/, 'á'], [/%c3%a2/gi, 'â'], [/c3%a4/gi, 'ä'], [/%c3%a7/gi, 'ç'], [/%c3%aa/gi, 'ê'], [/%C3%A8/gi, 'è'], [/%c3%a9/gi, 'é'], [/%c3%ab/gi, 'ë'], [/%0a/gi, '\r\n'], [/%22/gi, '"'], [/%20/gi, ' '], [/%3b/gi, ';']];
            if (i < caract.length) {
                return utf8Decode(str.replace(caract[i][0], caract[i][1]), i + 1);
            } else {
                return str;
            }
        }
        return utf8Decode;
    }
]).factory('exportToCSV', ['utf8Decode', function (utf8Decode) {
        return function (str, title) {
            if (!(window.ActiveXObject) && "ActiveXObject" in window) {
                var blob = new Blob([utf8Decode(str, 0).replace("data:text/csv;charset=UTF-8,", '')], {type: 'text/csv'});
                window.navigator.msSaveBlob(blob, title + '.csv');
            } else {
                angular.element('.export').attr({
                    'download': title + '.csv',
                    'href': str,
                    'target': '_blank'
                });
            }
        };
    }
]).factory('ngTableInMatrix', function () {
    return function (title, titles, colonne, data, colonneSize) {
        var pdf = {
            title: title,
            colonne: colonne.length,
            colSize: colonneSize,
            titles: titles,
            data: new Array(data.length)
        };
        for (var i = 0; i < data.length; i++) {
            pdf.data[i] = new Array(colonne.length);
        }
        for (var i in titles) {
            for (var j in data) {
                var ref = data[j][colonne[i]];
                if (data[j][colonne[i]] && colonne[i].match(/date/gi)) {
                    ref = new Date(data[j][colonne[i]]).toLocaleDateString();
                }
                pdf.data[j][i] = ref;
            }
        }
        return pdf;
    };
}).factory('cleanUpSpecialChars', function () {
    return function (str) {
        str = str.replace(/[ÀÁÂÃÄÅàáâãäå]/gi, "a");
        str = str.replace(/[ÈÉÊËéèêë]/gi, "e");
        str = str.replace(/[ïÎîï]/gi, "i");
        str = str.replace(/[ÔÖôö]/g, "o");
        str = str.replace(/[ÜÛûü]/g, "u");
        return str.replace(/[^a-zA-Z0-9]/gi, ''); // final clean up
    };
}).factory('createDate', function () {
    return function (str) {
        var date = new Date();
        date.setFullYear(parseInt(str.substring(0, 4)));
        date.setMonth(parseInt(str.substring(5, 7)) - 1);
        date.setDate(parseInt(str.substring(8, 10)));
        return date;
    };
});