/** @type {import('next').NextConfig} */
import path from "path";

//__dirname 선언
const __dirname = path.resolve();

const nextConfig = {
  images: {
    // domains: ['sitem.ssgcdn.com','kkoma.s3.ap-northeast-2.amazonaws.com'],
    domains: ["kkoma.s3.ap-northeast-2.amazonaws.com"],
    remotePatterns: [
      {
        protocol: "https",
        hostname: "chart.apis.google.com",
        port: "",
        pathname: "/chart/**",
      },
      {
        protocol: "https",
        hostname: "picsum.photos",
        port: "",
        pathname: "/**",
      },
      {
        protocol: "https",
        hostname: "lh3.googleusercontent.com",
        port: "",
        pathname: "/**",
      },
    ],
  },
  webpack(config) {
    config.module.rules.push({
      test: /\.svg$/,
      use: ["@svgr/webpack"],
    });
    return config;
  },
  sassOptions: {
    includePaths: [path.join(__dirname, "styles")],
    prependData: `@import "src/styles/_variables.scss"; @import "src/styles/_mixins.scss";`,
  },
  output: "standalone",
};

export default nextConfig;
