// Server-Sent Events (SSE) 客户端
export class SSEClient {
  private eventSource: EventSource | null = null;
  private reconnectAttempts = 0;
  private maxReconnectAttempts = 5;
  private reconnectDelay = 1000;

  constructor(private url: string) {}

  connect() {
    if (typeof window === 'undefined') return;

    this.eventSource = new EventSource(this.url);

    this.eventSource.onopen = () => {
      console.log('[SSE] Connected');
      this.reconnectAttempts = 0;
    };

    this.eventSource.onerror = (error) => {
      console.error('[SSE] Error:', error);
      this.handleReconnect();
    };

    return this.eventSource;
  }

  on(event: string, callback: (data: any) => void) {
    if (!this.eventSource) return;

    this.eventSource.addEventListener(event, (e: MessageEvent) => {
      try {
        const data = JSON.parse(e.data);
        callback(data);
      } catch (error) {
        console.error('[SSE] Parse error:', error);
      }
    });
  }

  disconnect() {
    if (this.eventSource) {
      this.eventSource.close();
      this.eventSource = null;
    }
  }

  private handleReconnect() {
    if (this.reconnectAttempts >= this.maxReconnectAttempts) {
      console.error('[SSE] Max reconnect attempts reached');
      return;
    }

    this.reconnectAttempts++;
    const delay = this.reconnectDelay * this.reconnectAttempts;

    console.log(`[SSE] Reconnecting in ${delay}ms (attempt ${this.reconnectAttempts})`);

    setTimeout(() => {
      this.disconnect();
      this.connect();
    }, delay);
  }
}

// React Hook for SSE
import { useEffect, useRef } from 'react';

export function useSSE<T>(
  url: string,
  event: string,
  onMessage: (data: T) => void,
  enabled = true
) {
  const clientRef = useRef<SSEClient | null>(null);

  useEffect(() => {
    if (!enabled) return;

    clientRef.current = new SSEClient(url);
    const eventSource = clientRef.current.connect();

    if (eventSource) {
      clientRef.current.on(event, onMessage);
    }

    return () => {
      clientRef.current?.disconnect();
    };
  }, [url, event, enabled]);

  return clientRef.current;
}

