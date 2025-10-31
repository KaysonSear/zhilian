import { StatusBar } from 'expo-status-bar';
import { StyleSheet, Text, View, ScrollView } from 'react-native';

export default function App() {
  return (
    <View style={styles.container}>
      <StatusBar style="auto" />
      <ScrollView contentContainerStyle={styles.content}>
        <View style={styles.header}>
          <Text style={styles.title}>智链分析溯源平台</Text>
          <Text style={styles.subtitle}>移动端监控应用</Text>
        </View>

        <View style={styles.card}>
          <Text style={styles.cardTitle}>🛡️ 实时监控</Text>
          <Text style={styles.cardDescription}>
            随时随地查看系统安全态势
          </Text>
        </View>

        <View style={styles.card}>
          <Text style={styles.cardTitle}>⚠️ 威胁告警</Text>
          <Text style={styles.cardDescription}>
            即时接收安全威胁通知
          </Text>
        </View>

        <View style={styles.card}>
          <Text style={styles.cardTitle}>📊 数据分析</Text>
          <Text style={styles.cardDescription}>
            直观展示流量和攻击统计
          </Text>
        </View>

        <View style={styles.card}>
          <Text style={styles.cardTitle}>🔍 溯源追踪</Text>
          <Text style={styles.cardDescription}>
            完整的安全事件溯源能力
          </Text>
        </View>

        <Text style={styles.footer}>
          基于 React Native + Expo SDK 构建
        </Text>
      </ScrollView>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#0f172a',
  },
  content: {
    padding: 20,
    paddingTop: 60,
  },
  header: {
    marginBottom: 30,
    alignItems: 'center',
  },
  title: {
    fontSize: 28,
    fontWeight: 'bold',
    color: '#ffffff',
    marginBottom: 8,
  },
  subtitle: {
    fontSize: 16,
    color: '#94a3b8',
  },
  card: {
    backgroundColor: '#1e293b',
    borderRadius: 12,
    padding: 20,
    marginBottom: 16,
    borderWidth: 1,
    borderColor: '#334155',
  },
  cardTitle: {
    fontSize: 20,
    fontWeight: '600',
    color: '#ffffff',
    marginBottom: 8,
  },
  cardDescription: {
    fontSize: 14,
    color: '#94a3b8',
    lineHeight: 20,
  },
  footer: {
    textAlign: 'center',
    color: '#64748b',
    marginTop: 20,
    fontSize: 12,
  },
});
