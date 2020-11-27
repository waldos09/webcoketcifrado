const path = require('path');
const express = require('express');
const app = express();
const NodeRSA = require('node-rsa');

//config del puerto

app.set('port', process.env.PORT || 3000);

//iniciar servidor
const server = app.listen(app.get('port'), () => {
    console.log("el servidor esta en el puerto ", app.get('port'));
});

//enviar archivos estaticos (public)
app.use(express.static(path.join(__dirname, 'public')));

//cifrado rsa
const key = new NodeRSA({ b: 1024 });
//var encryptedString = key.encrypt(str, 'base64');
//const decrypted = key.decrypt(encryptedString, 'utf8');


//websocket
const SocketIO = require('socket.io');
const io = SocketIO(server);
io.on('connection', (socket) => {
    console.log('new connection', socket.id);

    socket.on('chat:message', (data) => {
        console.log(data)
        io.sockets.emit('chat:message', data);
    });

    socket.on('chat:typing', (data) => {
        socket.broadcast.emit('chat:typing', data);
    });

    socket.on('message:cry', (data) => {

        crypt = key.encrypt(data, 'base64');
        console.log('El mensaje cifrado es; ', crypt);
        io.sockets.emit('message:cry', crypt);
    });

    socket.on('message:decry', (data) => {
        decrypt = key.decrypt(data, 'utf8');
        socket.broadcast.emit('message:decry', decrypt);
        console.log('El mensaje descifrado es: ', decrypt);

    });

});