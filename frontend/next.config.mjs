/** @type {import('next').NextConfig} */
import path from "path";

//__dirname 선언
const __dirname = path.resolve();

const nextConfig = {
  sassOptions: {
    includePaths: [path.join(__dirname, "styles")],
  },
};

export default nextConfig;
