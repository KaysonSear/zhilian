import { z } from 'zod';

// 认证相关Schema
export const loginSchema = z.object({
  username: z.string().min(3, '用户名至少3个字符').max(50, '用户名最多50个字符'),
  password: z.string().min(6, '密码至少6个字符').max(100, '密码最多100个字符'),
});

export const registerSchema = z.object({
  email: z.string().email('邮箱格式不正确'),
  username: z.string().min(3, '用户名至少3个字符').max(50, '用户名最多50个字符'),
  password: z.string().min(6, '密码至少6个字符').max(100, '密码最多100个字符'),
  name: z.string().optional(),
});

// 流量数据Schema
export const flowSchema = z.object({
  sourceIp: z.string().ip('源IP地址格式不正确'),
  destIp: z.string().ip('目标IP地址格式不正确'),
  sourcePort: z.number().int().min(0).max(65535, '源端口范围0-65535'),
  destPort: z.number().int().min(0).max(65535, '目标端口范围0-65535'),
  protocol: z.string().min(1, '协议不能为空'),
  packetLength: z.number().int().positive('数据包长度必须为正数'),
  flags: z.string().optional(),
  attackType: z.string().optional(),
  confidence: z.number().min(0).max(1).optional(),
});

// 规则Schema
export const ruleSchema = z.object({
  name: z.string().min(1, '规则名称不能为空').max(100, '规则名称最多100个字符'),
  description: z.string().max(500, '描述最多500个字符').optional(),
  ruleType: z.enum(['IP_RULE', 'PORT_RULE', 'PROTOCOL_RULE', 'PATTERN_RULE'], {
    errorMap: () => ({ message: '无效的规则类型' }),
  }),
  pattern: z.string().min(1, '匹配模式不能为空'),
  action: z.enum(['BLOCK', 'ALERT', 'ALLOW'], {
    errorMap: () => ({ message: '无效的操作类型' }),
  }),
  severity: z.enum(['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'], {
    errorMap: () => ({ message: '无效的严重程度' }),
  }),
  enabled: z.boolean().default(true),
});

export const updateRuleSchema = ruleSchema.partial();

// 告警Schema
export const updateAlertStatusSchema = z.object({
  status: z.enum(['PENDING', 'CONFIRMED', 'FALSE_POSITIVE', 'RESOLVED'], {
    errorMap: () => ({ message: '无效的告警状态' }),
  }),
});

// 查询参数Schema
export const paginationSchema = z.object({
  page: z.coerce.number().int().positive().default(1),
  pageSize: z.coerce.number().int().positive().max(100).default(20),
  sortBy: z.string().optional(),
  sortOrder: z.enum(['asc', 'desc']).optional(),
});

export const flowQuerySchema = paginationSchema.extend({
  sourceIp: z.string().optional(),
  destIp: z.string().optional(),
  protocol: z.string().optional(),
  attackType: z.string().optional(),
  startDate: z.string().datetime().optional(),
  endDate: z.string().datetime().optional(),
});

export const alertQuerySchema = paginationSchema.extend({
  severity: z.string().optional(),
  status: z.string().optional(),
  alertType: z.string().optional(),
  startDate: z.string().datetime().optional(),
  endDate: z.string().datetime().optional(),
});

// 文件上传Schema
export const fileUploadSchema = z.object({
  file: z.instanceof(File),
  type: z.enum(['pcap', 'csv'], {
    errorMap: () => ({ message: '只支持PCAP和CSV文件' }),
  }),
});

// 类型推断
export type LoginInput = z.infer<typeof loginSchema>;
export type RegisterInput = z.infer<typeof registerSchema>;
export type FlowInput = z.infer<typeof flowSchema>;
export type RuleInput = z.infer<typeof ruleSchema>;
export type UpdateRuleInput = z.infer<typeof updateRuleSchema>;
export type UpdateAlertStatusInput = z.infer<typeof updateAlertStatusSchema>;
export type PaginationInput = z.infer<typeof paginationSchema>;
export type FlowQueryInput = z.infer<typeof flowQuerySchema>;
export type AlertQueryInput = z.infer<typeof alertQuerySchema>;

