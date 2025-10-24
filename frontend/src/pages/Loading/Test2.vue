<template>
  <div style="color:#ffffff;">
    <h2>随机生成中国省份IP地址并查询省份</h2>
    <p>随机生成的IP地址是：{{ randomIP }}</p>
    <p v-if="province">查询到的省份是：{{ province }}</p>
    <button @click="generateRandomIP">生成随机IP地址</button>
  </div>
</template>

<script>
// 将IP地址转换为整数
function ipToInt(ip) {
  const parts = ip.split('.');
  return (parseInt(parts[0]) << 24 | parseInt(parts[1]) << 16 | parseInt(parts[2]) << 8 | parseInt(parts[3])) >>> 0;
}

// 将整数转换为IP地址
function intToIP(int) {
  return `${(int >>> 24) & 0xFF}.${(int >>> 16) & 0xFF}.${(int >>> 8) & 0xFF}.${int & 0xFF}`;
}

export default {
  data() {
    return {
      randomIP: '',
      province: ''
    };
  },
  methods: {
    generateRandomIP() {
      // 省份特定IP地址段
      const provinceIPRanges = [
        {province: "广东", range: {start: "183.0.0.0", end: "183.0.255.255"}},
        {province: "北京", range: {start: "110.96.0.0", end: "110.96.255.255"}},
        {province: "浙江", range: {start: "183.128.0.0", end: "183.128.255.255"}},
        {province: "山东", range: {start: "27.192.0.0", end: "27.192.255.255"}},
        {province: "江苏", range: {start: "49.64.0.0", end: "49.64.255.255"}},
        {province: "上海", range: {start: "180.175.0.0", end: "180.175.255.255"}},
        {province: "辽宁", range: {start: "175.160.0.0", end: "175.160.255.255"}},
        {province: "河南", range: {start: "115.48.0.0", end: "115.48.255.255"}},
        {province: "四川", range: {start: "182.128.0.0", end: "182.128.255.255"}},
        {province: "湖北", range: {start: "27.16.0.0", end: "27.16.255.255"}},
        {province: "福建", range: {start: "117.24.0.0", end: "117.24.255.255"}},
        {province: "湖南", range: {start: "223.144.0.0", end: "223.144.255.255"}},
        {province: "河北", range: {start: "120.0.0.0", end: "120.0.255.255"}},
        {province: "重庆", range: {start: "106.80.0.0", end: "106.80.255.255"}},
        {province: "山西", range: {start: "223.8.0.0", end: "223.8.255.255"}},
        {province: "江西", range: {start: "106.224.0.0", end: "106.224.255.255"}},
        {province: "陕西", range: {start: "219.244.0.0", end: "219.244.255.255"}},
        {province: "安徽", range: {start: "103.22.16.0", end: "103.22.31.255"}},
        {province: "黑龙江", range: {start: "203.90.192.0", end: "203.90.255.255"}},
        {province: "广西", range: {start: "202.103.240.0", end: "202.103.255.255"}},
        {province: "吉林", range: {start: "122.136.0.0", end: "122.136.255.255"}},
        {province: "云南", range: {start: "182.240.0.0", end: "182.240.255.255"}},
        {province: "天津", range: {start: "117.8.0.0", end: "117.8.255.255"}},
        {province: "内蒙古", range: {start: "123.178.0.0", end: "123.178.255.255"}},
        {province: "新疆", range: {start: "49.112.0.0", end: "49.112.255.255"}},
        {province: "甘肃", range: {start: "42.88.0.0", end: "42.88.255.255"}},
        {province: "贵州", range: {start: "61.189.128.0", end: "61.189.255.255"}},
        {province: "海南", range: {start: "223.198.0.0", end: "223.198.255.255"}},
        {province: "宁夏", range: {start: "14.134.0.0", end: "14.134.255.255"}},
        {province: "青海", range: {start: "103.22.100.0", end: "103.22.103.255"}},
        {province: "西藏", range: {start: "103.22.44.0", end: "103.22.47.255"}},
        {province: "香港", range: {start: "110.165.32.0", end: "110.165.47.255"}}
      ];

      // 随机选择一个省份的IP地址段
      const randomRange = provinceIPRanges[Math.floor(Math.random() * provinceIPRanges.length)];

      // 解析起始和结束IP地址为整数
      const startInt = ipToInt(randomRange.range.start);
      const endInt = ipToInt(randomRange.range.end);

      // 生成随机IP地址的整数表示
      const randomInt = Math.floor(Math.random() * (endInt - startInt + 1)) + startInt;

      // 转换为IP地址格式
      this.randomIP = intToIP(randomInt);
      console.log(this.randomIP)
      // 设置省份查询结果
      this.province = randomRange.province;
    }
  }
};
</script>

<style scoped>
/* 可选的样式 */
</style>
