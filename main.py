from fastapi import FastAPI
from database.connections import engine, Base
from models import *
from routers.user_routes import router as user_router  # Importamos el router

app = FastAPI()

# Crear tablas automáticamente
Base.metadata.create_all(bind=engine)

# Incluir routers
app.include_router(user_router)

@app.get("/")
def read_root():
    return {"message": "API funcionando correctamente 🚀-Frank test"}


print("Las tablas han sido creadas correctamente en la base de datos.")
