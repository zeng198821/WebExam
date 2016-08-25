/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var bootstrap={
    components : {},
    uids : {},
    ux : {},
    doc : document,
    window : window,
    isReady : false,
    byClass : function (a, b) {
        if (typeof b == "string") {
            b = bootstrap.byId(b);
        }
        return jQuery("." + a, b)[0];
    },
    getComponents : function () {
        var a = [];
        for (var d in bootstrap.components) {
            var b = bootstrap.components[d];
            if (b.isControl) {
                a.push(b);
            }
        }
        return a;
    },
    get : function (b) {
        if (!b) {
            return null;
        }
        if (bootstrap.isControl(b)) {
            return b;
        }
        if (typeof b == "string") {
            if (b.charAt(0) == "#") {
                b = b.substr(1);
            }
        }
        if (typeof b == "string") {
            return bootstrap.components[b];
        } else {
            var a = bootstrap.uids[b.uid];
            if (a && a.el == b) {
                return a;
            }
        }
        return null;
    },
    getbyUID : function (a) {
        return bootstrap.uids[a];
    },
    findControls : function (e, d) {
        if (!e) {
            return [];
        }
        d = d || bootstrap;
        var a = [];
        var f = bootstrap.uids;
        for (var c in f) {
            var g = f[c];
            var b = e.call(d, g);
            if (b === true || b === 1) {
                a.push(g);
                if (b === 1) {
                    break;
                }
            }
        }
        return a;
    },
    getChildControls : function (b) {
        var c = b.el ? b.el : b;
        var a = bootstrap.findControls(function (d) {
                if (!d.el || b == d) {
                    return false;
                }
                if (bootstrap.isAncestor(c, d.el) && d.within) {
                    return true;
                }
                return false;
            });
        return a;
    },
    emptyFn : function () {},
    createNameControls : function (h, g) {
        if (!h || !h.el) {
            return;
        }
        if (!g) {
            g = "_";
        }
        var f = h.el;
        var b = bootstrap.findControls(function (c) {
                if (!c.el || !c.name) {
                    return false;
                }
                if (bootstrap.isAncestor(f, c.el)) {
                    return true;
                }
                return false;
            });
        for (var e = 0, a = b.length; e < a; e++) {
            var j = b[e];
            var d = g + j.name;
            if (g === true) {
                d = j.name[0].toUpperCase() + j.name.substring(1, j.name.length);
            }
            h[d] = j;
        }
    },
    getsbyName : function (d, a) {
        var b = bootstrap.isControl(a);
        var e = a;
        if (a && b) {
            a = a.el;
        }
        a = bootstrap.byId(a);
        a = a || document.body;
        var c = bootstrap.findControls(function (g) {
                if (!g.el) {
                    return false;
                }
                if (g.name == d && bootstrap.isAncestor(a, g.el)) {
                    return true;
                }
                return false;
            }, this);
        if (b && c.length == 0 && e && e.getbyName) {
            var f = e.getbyName(d);
            if (f) {
                c.push(f);
            }
        }
        return c;
    },
    getbyName : function (b, a) {
        return bootstrap.getsbyName(b, a)[0];
    },
    getParams : function (b) {
        if (!b) {
            b = location.href;
        }
        b = b.split("?")[1];
        var g = {};
        if (b) {
            var e = b.split("&");
            for (var d = 0, a = e.length; d < a; d++) {
                var f = e[d].split("=");
                try {
                    g[f[0]] = decodeURIComponent(unescape(f[1]));
                } catch (c) {}
            }
        }
        return g;
    },
    reg : function (a) {
        this.components[a.id] = a;
        this.uids[a.uid] = a;
    },
    unreg : function (a) {
        delete bootstrap.components[a.id];
        delete bootstrap.uids[a.uid];
    },
    classes : {},
    uiClasses : {},
    getClass : function (a) {
        if (!a) {
            return null;
        }
        return this.classes[a.toLowerCase()];
    },
    getClassByUICls : function (a) {
        return this.uiClasses[a.toLowerCase()];
    },
    idPre : "mini-",
    idIndex : 1,
    newId : function (a) {
        return (a || this.idPre) + this.idIndex++;
    },
    copyTo : function (c, b) {
        if (c && b) {
            for (var a in b) {
                c[a] = b[a];
            }
        }
        return c;
    },
    copyIf : function (c, b) {
        if (c && b) {
            for (var a in b) {
                if (bootstrap.isNull(c[a])) {
                    c[a] = b[a];
                }
            }
        }
        return c;
    },
    createDelegate : function (b, a) {
        if (!b) {
            return function () {};
        }
        return function () {
            return b.apply(a, arguments);
        };
    },
    isControl : function (a) {
        return !!(a && a.isControl);
    },
    isElement : function (a) {
        return a && a.appendChild;
    },
    isDate : function (a) {
        return !!(a && a.getFullYear);
    },
    isArray : function (a) {
        return !!(a && !!a.unshift);
    },
    isNull : function (a) {
        return a === null || a === undefined;
    },
    isEmpty: function (value, allowEmptyString) {
        return (value === null) || (value === undefined) || (!allowEmptyString ? value === '' : false) || ($.isArray(value) && value.length === 0);
    },
    isNumber : function (a) {
        return !isNaN(a) && typeof a == "number";
    },
    isEquals : function (d, c) {
        if (d !== 0 && c !== 0) {
            if ((bootstrap.isNull(d) || d == "") && (bootstrap.isNull(c) || c == "")) {
                return true;
            }
        }
        if (d && c && d.getFullYear && c.getFullYear) {
            return d.getTime() === c.getTime();
        }
        if (typeof d == "object" && typeof c == "object") {
            return d === c;
        }
        return String(d) === String(c);
    },
    forEach : function (g, f, c) {
        var d = g.clone();
        for (var b = 0, a = d.length; b < a; b++) {
            var e = d[b];
            if (f.call(c, e, b, g) === false) {
                break;
            }
        }
    },
    sort : function (c, b, a) {
        a = a || c;
        c.sort(b);
    },
    removeNode : function (a) {
        jQuery(a).remove();
    },
    elWarp : document.createElement("div")
    
    
    
    
    
    
    
};



