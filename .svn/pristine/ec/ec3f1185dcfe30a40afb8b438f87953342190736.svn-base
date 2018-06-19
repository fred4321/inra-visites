/* * Copyright (C) 2014 thurgi*/
angular.module('inra').factory('tableModel', ['CONST', 'ngTableParams',
    function (CONST, ngTableParams) {
        return function (data) {
            this.data = data;
            this.filter = new Object();
            this.params1 = {
                page: 1,
                count: CONST.pagination.nbPages[0],
                filter: this.filter
            };
            this.params2 = {
                getRow: function () {
                    return this.data;
                },
                counts: [],
                total: this.data.length,
                getData: function ($defer, params) {
                    $defer.resolve(data.slice(0, params.page() * params.count()));
                }
            };

            this.ngTable = new ngTableParams(this.params1, this.params2);

            this.setPage = function (p) {
                this.ngTable.page(p);
            };

            this.delete = function (id) {
                for (var i in this.data) {
                    if (this.data[i].id === id) {
                        data.splice(i, 1);
                    }
                }
                this.ngTable.reload();
            };

            this.deleteGroupe = function (arrayId) {
                for (var j in arrayId) {
                    for (var i in this.data) {
                        if (this.data[i].id === arrayId[j].id) {
                            data.splice(i, 1);
                        }
                    }
                }
                this.ngTable.reload();
            };

            this.reload = function () {
                this.ngTable.reload();
            };
        };
    }]);