// 用户相关类型
export enum Role {
  USER = 'USER',
  ADMIN = 'ADMIN',
  ANALYST = 'ANALYST',
}

export interface User {
  id: string;
  email: string;
  username: string;
  name?: string;
  role: Role;
  createdAt: Date;
  updatedAt: Date;
}

// 流量数据类型
export interface FlowData {
  id: string;
  timestamp: Date;
  sourceIp: string;
  destIp: string;
  sourcePort: number;
  destPort: number;
  protocol: string;
  packetLength: number;
  flags?: string;
  attackType?: string;
  confidence?: number;
  isAnomalous: boolean;
  geoInfo?: GeoInfo;
  metadata?: Record<string, any>;
}

export interface GeoInfo {
  country?: string;
  region?: string;
  city?: string;
  latitude?: number;
  longitude?: number;
}

// 规则类型
export enum RuleType {
  IP_RULE = 'IP_RULE',
  PORT_RULE = 'PORT_RULE',
  PROTOCOL_RULE = 'PROTOCOL_RULE',
  PATTERN_RULE = 'PATTERN_RULE',
}

export enum RuleAction {
  BLOCK = 'BLOCK',
  ALERT = 'ALERT',
  ALLOW = 'ALLOW',
}

export enum Severity {
  LOW = 'LOW',
  MEDIUM = 'MEDIUM',
  HIGH = 'HIGH',
  CRITICAL = 'CRITICAL',
}

export interface Rule {
  id: string;
  name: string;
  description?: string;
  ruleType: string;
  pattern: string;
  action: string;
  severity: string;
  enabled: boolean;
  createdAt: Date;
  updatedAt: Date;
}

// 告警类型
export enum AlertStatus {
  PENDING = 'PENDING',
  CONFIRMED = 'CONFIRMED',
  FALSE_POSITIVE = 'FALSE_POSITIVE',
  RESOLVED = 'RESOLVED',
}

export interface Alert {
  id: string;
  timestamp: Date;
  severity: string;
  alertType: string;
  sourceIp: string;
  destIp?: string;
  title: string;
  description: string;
  status: string;
  flowId?: string;
  ruleId?: string;
  metadata?: Record<string, any>;
  createdAt: Date;
  updatedAt: Date;
}

// 主机监控类型
export interface HostMetrics {
  id: string;
  timestamp: Date;
  hostname: string;
  cpuUsage: number;
  memoryUsage: number;
  diskUsage: number;
  networkIn: number;
  networkOut: number;
  processes?: ProcessInfo[];
  metadata?: Record<string, any>;
}

export interface ProcessInfo {
  pid: number;
  name: string;
  cpuUsage: number;
  memoryUsage: number;
}

// API响应类型
export interface ApiResponse<T = any> {
  success: boolean;
  data?: T;
  error?: string;
  message?: string;
}

export interface PaginatedResponse<T> {
  data: T[];
  total: number;
  page: number;
  pageSize: number;
  hasMore: boolean;
}

// WebSocket消息类型
export enum WebSocketMessageType {
  FLOW_UPDATE = 'FLOW_UPDATE',
  ALERT_NEW = 'ALERT_NEW',
  HOST_METRICS = 'HOST_METRICS',
  SYSTEM_STATUS = 'SYSTEM_STATUS',
}

export interface WebSocketMessage<T = any> {
  type: WebSocketMessageType;
  data: T;
  timestamp: Date;
}

