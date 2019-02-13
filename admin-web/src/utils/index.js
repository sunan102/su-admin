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

export function isEmpty(str) {
  return str != null && str != ''
}

export function createEchartData(list) {
  let data = { value: [], text: [] };
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