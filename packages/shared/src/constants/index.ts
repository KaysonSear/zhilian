// API端点常量
export const API_ENDPOINTS = {
  // 认证
  AUTH: {
    LOGIN: '/api/auth/login',
    LOGOUT: '/api/auth/logout',
    REGISTER: '/api/auth/register',
    PROFILE: '/api/auth/profile',
  },
  // 流量
  FLOWS: {
    LIST: '/api/flows',
    DETAIL: '/api/flows/:id',
    UPLOAD: '/api/flows/upload',
    STATS: '/api/flows/stats',
  },
  // 规则
  RULES: {
    LIST: '/api/rules',
    DETAIL: '/api/rules/:id',
    CREATE: '/api/rules',
    UPDATE: '/api/rules/:id',
    DELETE: '/api/rules/:id',
  },
  // 告警
  ALERTS: {
    LIST: '/api/alerts',
    DETAIL: '/api/alerts/:id',
    UPDATE_STATUS: '/api/alerts/:id/status',
  },
  // 主机监控
  HOST: {
    METRICS: '/api/host/metrics',
    CURRENT: '/api/host/current',
  },
  // 统计
  STATS: {
    DASHBOARD: '/api/stats/dashboard',
    TRAFFIC: '/api/stats/traffic',
    THREATS: '/api/stats/threats',
  },
} as const;

// WebSocket端点
export const WS_ENDPOINTS = {
  REALTIME: '/ws/realtime',
  HOST_METRICS: '/ws/host',
} as const;

// 分页默认值
export const PAGINATION = {
  DEFAULT_PAGE: 1,
  DEFAULT_PAGE_SIZE: 20,
  MAX_PAGE_SIZE: 100,
} as const;

// 时间格式
export const DATE_FORMATS = {
  FULL: 'YYYY-MM-DD HH:mm:ss',
  DATE: 'YYYY-MM-DD',
  TIME: 'HH:mm:ss',
  SHORT: 'MM-DD HH:mm',
} as const;

// 协议类型
export const PROTOCOLS = {
  TCP: 'TCP',
  UDP: 'UDP',
  ICMP: 'ICMP',
  HTTP: 'HTTP',
  HTTPS: 'HTTPS',
  DNS: 'DNS',
  FTP: 'FTP',
  SSH: 'SSH',
  TELNET: 'TELNET',
} as const;

// 攻击类型
export const ATTACK_TYPES = {
  NORMAL: 'Normal',
  DOS: 'DoS',
  PROBE: 'Probe',
  R2L: 'R2L',
  U2R: 'U2R',
  SQL_INJECTION: 'SQL Injection',
  XSS: 'XSS',
  BRUTE_FORCE: 'Brute Force',
  PORT_SCAN: 'Port Scan',
  UNKNOWN: 'Unknown',
} as const;

// 颜色主题
export const SEVERITY_COLORS = {
  LOW: '#52c41a',
  MEDIUM: '#faad14',
  HIGH: '#fa8c16',
  CRITICAL: '#f5222d',
} as const;

export const STATUS_COLORS = {
  PENDING: '#1890ff',
  CONFIRMED: '#fa8c16',
  FALSE_POSITIVE: '#52c41a',
  RESOLVED: '#8c8c8c',
} as const;

