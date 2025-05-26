import os
import sys
import pytest
from fastapi.testclient import TestClient
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
from sqlalchemy.pool import StaticPool
sys.path.append(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

# test
# Importar la aplicación FastAPI y la base de datos del proyecto
from main import app
from models.persistence import Base

# Configuración de la base de datos SQLite en memoria para pruebas
DATABASE_URL_TEST = "sqlite:///:memory:"
# Crear el motor de la base de datos en memoria (StaticPool para usar misma conexión en hilos)
engine_test = create_engine(
    DATABASE_URL_TEST, connect_args={"check_same_thread": False}, poolclass=StaticPool
)
TestingSessionLocal = sessionmaker(bind=engine_test, autocommit=False, autoflush=False)

# Crear las tablas en la base de datos de pruebas
Base.metadata.create_all(bind=engine_test)

# Función de dependencia alternativa que devuelve una sesión de la base de datos de pruebas
def override_get_db():
    """
    Abre una sesión de base de datos SQLite en memoria para usar en las pruebas.
    Cierra la sesión al finalizar.
    """
    db = TestingSessionLocal()
    try:
        yield db
    finally:
        db.close()

# Sobrescribir las dependencias de base de datos en la aplicación para usar la base de pruebas
# Las rutas de usuario y audiometría utilizan funciones de controlador que dependen de DataBaseSession.get_db
from controllers.user.imp import auth_user_controller_imp, add_user_controller_imp, get_user_imp, get_person_rol_imp, up_date_person_imp, remove_person_imp
from controllers.audiometry.imp import add_audiometry_imp, add_audiometry_results_imp, get_audiometry_imp

app.dependency_overrides[auth_user_controller_imp.db_session.get_db] = override_get_db
app.dependency_overrides[add_user_controller_imp.db_session.get_db] = override_get_db
app.dependency_overrides[get_user_imp.db_session.get_db] = override_get_db
app.dependency_overrides[get_person_rol_imp.db_session.get_db] = override_get_db
app.dependency_overrides[up_date_person_imp.db_session.get_db] = override_get_db
app.dependency_overrides[remove_person_imp.db_session.get_db] = override_get_db
app.dependency_overrides[add_audiometry_imp.db_connection.get_db] = override_get_db
app.dependency_overrides[add_audiometry_results_imp.db_session.get_db] = override_get_db
app.dependency_overrides[get_audiometry_imp.db_session.get_db] = override_get_db

# Fixture de cliente de pruebas de FastAPI
@pytest.fixture()
def client():
    """
    Cliente de pruebas que utiliza la aplicación FastAPI con la base de datos en memoria.
    Antes de cada prueba se aseguran las tablas vacías.
    """
    # Limpiar y recrear las tablas antes de cada prueba para aislamiento
    Base.metadata.drop_all(bind=engine_test)
    Base.metadata.create_all(bind=engine_test)
    yield TestClient(app)