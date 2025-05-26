from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from fastapi.openapi.utils import get_openapi

from models.persistence import engine, Base
from routers.user_routes import router as user_router
from routers.audiometry_routes import router as audiometry_router

app = FastAPI(
    title="API de Audiometrías",
    version="1.0.0",
    description="Documentación de la API de usuarios y audiometrías."
)

# CORS
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # Cambia esto si usas dominios específicos
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# Crear las tablas automáticamente
Base.metadata.create_all(bind=engine)
print("Las tablas han sido creadas correctamente en la base de datos.")

# Incluir routers
app.include_router(user_router)
app.include_router(audiometry_router)

# Ruta raíz
@app.get("/", tags=["Root"])
def read_root():
    return {"message": "API funcionando correctamente"}

# Personalizar el esquema OpenAPI para documentar el uso del token JWT
def custom_openapi():
    if app.openapi_schema:
        return app.openapi_schema
    openapi_schema = get_openapi(
        title=app.title,
        version=app.version,
        description=app.description,
        routes=app.routes,
    )
    openapi_schema["components"]["securitySchemes"] = {
        "BearerAuth": {
            "type": "http",
            "scheme": "bearer",
            "bearerFormat": "JWT"
        }
    }
    for path in openapi_schema["paths"].values():
        for method in path.values():
            if "security" not in method:
                method["security"] = [{"BearerAuth": []}]
    app.openapi_schema = openapi_schema
    return app.openapi_schema

app.openapi = custom_openapi
