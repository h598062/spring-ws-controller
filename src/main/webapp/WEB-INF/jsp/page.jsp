<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<label for="message"><input type="text" id="message" value="Hello"></label>
<button onclick="ws.send(document.getElementById('message').value)">Send</button>

<script>
    let ws = new WebSocket((window.location.protocol === 'https:' ? 'wss://' : 'ws://') + window.location.host + '/websocket');
    ws.onopen = function () {
        console.log('WebSocket connection opened');
    };

    ws.onmessage = function (event) {
        console.log('Message from server: ' + event.data);
    };

    ws.onclose = function () {
        console.log('WebSocket connection closed');
    };

</script>
</body>
</html>
