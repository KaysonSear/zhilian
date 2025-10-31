import { NextRequest } from 'next/server';

// SSE响应流
export async function GET(request: NextRequest) {
  const encoder = new TextEncoder();

  const customReadable = new ReadableStream({
    async start(controller) {
      // 发送初始消息
      const message = `data: ${JSON.stringify({ type: 'connected', timestamp: new Date() })}\n\n`;
      controller.enqueue(encoder.encode(message));

      // 每2秒发送一次模拟的主机指标
      const interval = setInterval(() => {
        const metrics = {
          type: 'metrics',
          data: {
            timestamp: new Date(),
            cpuUsage: Math.random() * 100,
            memoryUsage: Math.random() * 100,
            diskUsage: Math.random() * 100,
            networkIn: Math.random() * 1000,
            networkOut: Math.random() * 1000,
          },
        };

        const message = `data: ${JSON.stringify(metrics)}\n\n`;
        controller.enqueue(encoder.encode(message));
      }, 2000);

      // 监听客户端断开连接
      request.signal.addEventListener('abort', () => {
        clearInterval(interval);
        controller.close();
      });
    },
  });

  return new Response(customReadable, {
    headers: {
      'Content-Type': 'text/event-stream',
      'Cache-Control': 'no-cache',
      'Connection': 'keep-alive',
    },
  });
}

