import { NextRequest } from 'next/server';

// SSE: 实时告警推送
export async function GET(request: NextRequest) {
  const encoder = new TextEncoder();

  const customReadable = new ReadableStream({
    async start(controller) {
      // 发送初始消息
      const message = `data: ${JSON.stringify({ type: 'connected', timestamp: new Date() })}\n\n`;
      controller.enqueue(encoder.encode(message));

      // 模拟随机告警生成
      const interval = setInterval(() => {
        // 30%概率生成告警
        if (Math.random() < 0.3) {
          const severities = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'];
          const types = ['端口扫描', 'SQL注入', 'XSS攻击', 'DDoS攻击', 'SSH暴力破解'];

          const alert = {
            type: 'alert',
            data: {
              id: `alert-${Date.now()}`,
              timestamp: new Date(),
              severity: severities[Math.floor(Math.random() * severities.length)],
              alertType: types[Math.floor(Math.random() * types.length)],
              sourceIp: `192.168.1.${Math.floor(Math.random() * 255)}`,
              title: `检测到可疑活动`,
              description: `来自异常IP的攻击尝试`,
              status: 'PENDING',
            },
          };

          const message = `data: ${JSON.stringify(alert)}\n\n`;
          controller.enqueue(encoder.encode(message));
        }
      }, 5000);

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

