<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
    <meta charset="UTF-8">
    <title>제목 입력</title>
    <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
</head>
<body>
    <div id="vueScript">
        <label for="userInput">아이디를 입력하세요:</label>
        <input type="text" id="userInput" v-model="charId">
        <button @click="sendToOcidApi" @keyup.enter="sendToOcidApi">확인</button>
   		 {{ocid}}
    </div>
</body>

<script type="text/javascript">

   (function (Vue) {
        window.vm = new Vue({
            el: "#vueScript",
            data: function () {
                return {
                    ocid : '',
                	charId: ''
                    
                }
            },
            methods: {
                /* 엔터 */
                handleEnterKey: function (event) {
                    if (event.key === 'Enter') {
                        this.sendToOcidApi();
                    }
                },
                /* 아이디 입력 */
                sendToOcidApi: function () {
                    var vm = this;
                    axios.get("/ocidApi/" + vm.charId)
                        .then(function (response) {
                            alert(response.data);
                            vm.ocid = response.data;
                            vm.sendToBasicApi();
                        })
                        .catch(function (error) {
                            console.error("Error:", error);
                        });
                },
                
                /*캐릭터 기본조회*/
                sendToBasicApi: function () {
                    var vm = this;
                    axios.get("/basicApi/" + vm.ocid)
                        .then(function (response) {
                            alert(response.data);
                        });
                }
                
            },
            mounted: function () {
                /* 엔터 */
                var inputElement = document.getElementById("userInput");
                inputElement.addEventListener("keydown", this.handleEnterKey);
            }
        });
    })(Vue);
</script>

</html>