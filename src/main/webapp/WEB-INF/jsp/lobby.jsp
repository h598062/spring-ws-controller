<%--@elvariable id="lobbyId" type="java.lang.String"--%>
<%--@elvariable id="spiller" type="java.lang.String"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="nb">
<head>
    <title>Lobby</title>
    <!--
    JSPM Generator Import Map
    Edit URL: https://generator.jspm.io/#U2NgYGBkDM0rySzJSU1hcCguyc8t0AeTWcUO5noGega6SakliaYAYTzJAykA
  -->
    <script type="importmap">
        {
          "imports": {
            "@stomp/stompjs": "https://ga.jspm.io/npm:@stomp/stompjs@7.0.0/esm6/index.js"
          }
        }
    </script>

    <!-- ES Module Shims: Import maps polyfill for modules browsers without import maps support (all except Chrome 89+) -->
    <script
            async
            src="https://ga.jspm.io/npm:es-module-shims@1.5.1/dist/es-module-shims.js"
            crossorigin="anonymous"
    ></script>
</head>
<body>
<h1>Lobby ${lobbyId}</h1>
<button id="raise">Raise</button>
<button id="call">Call</button>
<button id="fold">Fold</button>

<label for="raiseNum">Number:</label>
<input type="number" id="raiseNum" name="numberInput" value="0">

<script type="module">
    import {Client} from '@stomp/stompjs';

    const lobbyId = '${lobbyId}';
    const spiller = '${spiller}';

    const client = new Client({brokerURL: 'ws://localhost:8080/lobby-ws'});
    client.onConnect = (frame) => {
        console.log('Connected: ' + frame);
        client.subscribe('/lobby/status/' + lobbyId, handleMessage);
        let body = {"status": "JOINED", "spiller": spiller};
        client.publish({destination: '/lobby/spillerstatus/' + lobbyId, body: JSON.stringify(body)});
    }

    function handleMessage(message) {
        console.log('Received: ' + message.body);
    }

    client.onWebSocketError = (error) => {
        console.error('Error with websocket', error);
    };

    client.onStompError = (frame) => {
        console.error('Broker reported error: ' + frame.headers['message']);
        console.error('Additional details: ' + frame.body);
    };

    client.activate();

    function raise() {
        const number = document.getElementById('raiseNum').value;
        let body = {"trekk": "RAISE", "spiller": spiller, "verdi": number};
        console.log('Raising with ' + number);
        console.log(body);
        client.publish({destination: '/lobby/trekk/' + lobbyId, body: JSON.stringify(body)});
    }

    function call() {
        let body = {"trekk": "CALL", "spiller": spiller, "verdi": 0};
        console.log('Calling');
        console.log(body);
        client.publish({destination: '/lobby/trekk/' + lobbyId, body: JSON.stringify(body)});
    }

    function fold() {
        let body = {"trekk": "FOLD", "spiller": spiller, "verdi": 0};
        console.log('Folding');
        console.log(body);
        client.publish({destination: '/lobby/trekk/' + lobbyId, body: JSON.stringify(body)});
    }

    document.getElementById('raise').addEventListener('click', raise);
    document.getElementById('call').addEventListener('click', call);
    document.getElementById('fold').addEventListener('click', fold);
</script>
</body>
</html>
