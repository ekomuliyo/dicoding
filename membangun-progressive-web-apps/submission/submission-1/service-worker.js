const CACHE_NAME = "galeriku-v1";

const scripts = [
    "materialize.min.js",
    "nav-bar.js"
].map(script => `/js/${script}`);

const css = [
    "materialize.min.css"
].map(css => `'/css/${css}`);

const imageAssets = [
    "office.jpg",
    "pp.png"
].map(image => `/assets/${image}`);

const urlsToCache = [
    "/",
    "index.html",
    "nav-top.html",
    "nav-side.html",
    "/galeriku.html",
    "/login.html",
    "/register.html",
    "/tentang.html",
    ...imageAssets,
    ...css,
    ...scripts
];


