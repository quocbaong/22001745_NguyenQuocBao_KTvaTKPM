from flask import Flask
app = Flask(__name__)

@app.route("/")
def hello():
    return "<h1>Hello from Python Flask in Docker!</h1><p>Bài 9 đã hoàn thành thành công.</p>"

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000)
