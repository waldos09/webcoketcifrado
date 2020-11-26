const path = require('path');
const express = require('express');
const app = express();


//config del puerto
app.set('port', process.env.PORT || 3000);

//iniciar servidor
const server = app.listen(app.get('port'), () => {
    console.log("el servidor esta en el puerto ", app.get('port'));
});

//enviar archivos estaticos (public)
app.use(express.static(path.join(__dirname, 'public')));

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
        console.log(data);
        socket.broadcast.emit('chat:typing', data);
    })

});