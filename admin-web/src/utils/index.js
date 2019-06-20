/**
 * Created by jiachenpan on 16/11/18.
 */

export function formatTime(time, fmt) {
    var o = {
        "M+": time.getMonth() + 1, // 月份
        "d+": time.getDate(), // 日
        "h+": time.getHours(), // 小时
        "m+": time.getMinutes(), // 分
        "s+": time.getSeconds(), // 秒
        "q+": Math.floor((time.getMonth() + 3) / 3), // 季度
        "S": time.getMilliseconds()
        // 毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (time.getFullYear() + "")
            .substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

export function isNotEmpty(str) {
    return str != null && str != ''
}

export function createEchartData(list) {
    let data = {value: [], text: []};
    if (list && list.length > 0) {
        for (var i = 0; i < list.length; i++) {
            data.value.push(list[i].value);
            data.text.push(list[i].text + '\n');
        }
    }
    return data;
}

export function downloadCsv(response) {
    const blob = new Blob([response.data], {
        type: "text/csv"
    });
    const fileName = formatTime(new Date(), 'yyyyMMddmmhhss') + ".csv";
    if ("download" in document.createElement("a")) {
        // 非IE下载
        const elink = document.createElement("a");
        elink.download = fileName;
        elink.style.display = "none";
        elink.href = URL.createObjectURL(blob);
        document.body.appendChild(elink);
        elink.click();
        URL.revokeObjectURL(elink.href); // 释放URL 对象
        document.body.removeChild(elink);
    } else {
        // IE10+下载
        navigator.msSaveBlob(blob, fileName);
    }
}

/**
 * 创建菜单树对象
 * @param list
 * @param menuList
 * @returns {Array}
 */
export function createMenuTree(menuList, pid) {
    let retMenuList = [];
    if (menuList && menuList.length > 0) {
        for (let i = 0; i < menuList.length; i++) {
            if (menuList[i].pid == pid) {
                menuList[i].children = createMenuTree(menuList, menuList[i].adminMenuId);
                const object2 = Object.assign({
                    id: menuList[i].adminMenuId,
                    label: menuList[i].menuName

                }, menuList[i]);
                retMenuList.push(object2);
            }
        }
    }
    return retMenuList;
}

/**
 * 创建菜单树对象
 * @param list
 * @param menuList
 * @returns {Array}
 */
export function createMenuTree_old(list, menuList) {
    var result = [];
    if (list && list.length > 0) {
        for (let i = 0; i < list.length; i++) {
            if (list[i].hidden) {
                continue;
            }
            var node = {
                id: list[i].path,
                label: list[i].title,
                type: 1
            };
            if (list[i].children && list[i].children.length > 0) {
                node.children = createMenuTree(list[i].children, menuList);
            }
            //将请求得到的menu信息整合到menuList中
            if (menuList && menuList.length > 0) {
                for (let j = 0; j < menuList.length; j++) {
                    if (menuList[j].pid == node.id) {
                        if (!node.children) {
                            node.children = [];
                        }
                        const object2 = Object.assign({
                            id: menuList[j].adminMenuId,
                            label: menuList[j].menuName,
                            type: 2,

                        }, menuList[j]);
                        node.children.push(object2);
                    }
                }
            }
            result.push(node);

        }
    }
    return result;
}