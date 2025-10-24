import asyncio
import websockets

async def connect_to_websocket():
    async with websockets.connect('ws://localhost:8808/transmission') as websocket:
        # 连接到WebSocket服务端     👆必须是ws或者wss
        print('已连接到WebSocket服务端')

        while True:
            message = input('请输入要发送的消息：')
            await websocket.send(message)  # 发送消息到服务端
            print('已发送消息 -> ', message)

            response = await websocket.recv()  # 接收服务端的消息
            print('收到消息 -> ', response)

asyncio.get_event_loop().run_until_complete(connect_to_websocket())
