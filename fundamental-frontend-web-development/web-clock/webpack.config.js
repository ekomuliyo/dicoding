const path = require("path");
const HtmlWebpackPlugin = require("html-webpack-plugin");

module.exports = {
    entry: "./src/index.js",
    output: {
        path: path.resolve(__dirname, "dist"),
        filename: "bundle.js"
    },
    mode: "production",
    module: {
        rules: [
            //  style and css loader
            {   
                test: /\.css$/,
                use: [
                    {
                        loader: "style-loader"
                    },
                    {
                        loader: "css-loader"
                    }
                ]
            },
            // babel loader adalah memungkinan kode ES6 dapat berjalan di semua versi browser
            {
                test: /\.js$/,
                exclude: "/node_modules", // mengecualikan seluruh file di folder node_modules, karena kita tidak butuh file2 js di folder ini
                use: [
                    {
                        loader: "babel-loader",
                        options: {
                            presets: ["@babel/preset-env"] // memungkinkan kita menggunakan ES terbaru tanpa mendefinisikan versi spesifik
                        }
                    }
                ]
            }
        ]
    },
    // plugin
    plugins: [
        // HTML Webpack Plugin
        new HtmlWebpackPlugin({
            template: "./src/template.html", // sumber nya
            filename: "index.html" // tujuan nya di folder /dist
        })
    ]
}