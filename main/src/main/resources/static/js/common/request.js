/**
 * 获取 username
 * 更新 Vue实例的 username
 * @param vue Vue实例
 */
function setUsername(vue) {
    axios({
        url: "/common/username",
        method: "GET",
        responseType: "json"
    }).then(function (response) {
        vue.username = response.data.username
    })
}

/**
 * 获取区域
 */
function getRegions(vue){
    axios({
        url: "/common/regions",
        method: "GET",
        responseType: "json"
    }).then(function (response) {
        vue.regions = response.data.regions;
    }).catch(function (err) {

    })
}