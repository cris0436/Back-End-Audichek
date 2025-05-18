from fastapi import FastAPI
from models.persistence import engine, Base
from models import *
from routers.user_routes import router as user_router
from routers.audiometry_routes import router as audiometry_router  # Importamos el router
from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware

app = FastAPI()

# Agrega esto
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# Crear tablas automáticamente
Base.metadata.create_all(bind=engine)

# Incluir routers
app.include_router(user_router)
app.include_router(audiometry_router)  # Incluimos el router de audiometrías

@app.get("/")
def read_root():
    return {"message": "API funcionando correctamente"}


print("Las tablas han sido creadas correctamente en la base de datos.")
