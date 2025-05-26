from models.entities.Frecuency import Frecuency
from models.entities.Decibel import Decibel
from controllers.audiometry.imp.get_frecuency_imp import GetFrequencyControllerImp
from controllers.audiometry.imp.get_decibel_imp import GetDecibelControllerImp
from controllers.audiometry.imp.get_ear_lever_imp import GetEarLeverImp
from models.entities.AudiometryResults import AudiometryResults
from models.entities.Frecuency import Frecuency
from models.entities.Decibel import Decibel
from schemas.Audiometry import Audiometry, DecibelFrequency

import pytest
from fastapi import HTTPException, status, Depends
def test_usuario_autenticacion_login_y_token(client):
    """
    Prueba el proceso de autenticación:
    1. Registro de un nuevo usuario.
    2. Login con credenciales correctas para obtener un token JWT.
    3. Acceso a un endpoint protegido con el token obtenido.
    """
    # Datos de un usuario de prueba para registro
    nuevo_usuario = {
        "cedula": "123456789",
        "name": "Carlos Perez",
        "email": "carlos@example.com",
        "birth_date": "1990-01-01",
        "username": "carlosp",
        "password": "secreto123",
        "rol": "Paciente",
        "ocupation": "Tester"
    }

    # 1. Registrar un nuevo usuario mediante el endpoint POST /users/
    resp_registro = client.post("/users/", json=nuevo_usuario)
    assert resp_registro.status_code == 200
    data_registro = resp_registro.json()
    # Comprobar que la respuesta contiene el username y email correctos
    assert data_registro["username"] == nuevo_usuario["username"]
    assert data_registro["person"]["email"] == nuevo_usuario["email"]
    # Guardar el username para utilizarlo en login (el password lo tenemos en nuevo_usuario)

    # 2. Autenticarse con el usuario registrado (endpoint POST /users/auth)
    credenciales = {"username": nuevo_usuario["username"], "password": nuevo_usuario["password"]}
    resp_login = client.post("/users/auth", json=credenciales)
    assert resp_login.status_code == 200
    data_login = resp_login.json()
    # La respuesta de autenticación debe contener el token de acceso y el tipo de token
    assert "access_token" in data_login
    assert data_login["token_type"] == "bearer"
    token_jwt = data_login["access_token"]
    assert token_jwt is not None and token_jwt != ""
    # (Opcional) Verificar que el token JWT tiene el formato esperado (ejemplo: empieza con 'ey' típico de JWT)
    assert token_jwt.startswith("ey")

    # 3. Usar el token JWT para acceder a un endpoint protegido (/users/get-session)
    headers = {"Authorization": f"Bearer {token_jwt}"}
    resp_session = client.get("/users/get-session", headers=headers)
    assert resp_session.status_code == 200
    data_session = resp_session.json()
    # Verificar que el endpoint protegido devuelve los datos del usuario actual
    assert data_session["username"] == nuevo_usuario["username"]
    assert data_session["person"]["email"] == nuevo_usuario["email"]
    # También podemos comprobar que el ID del usuario coincide con el retornado en el registro
    assert data_session["id"] == data_registro["id"]

    # (Negativo) Intentar acceder al endpoint protegido sin token debería devolver 401 Unauthorized
    resp_sin_token = client.get("/users/get-session")
    assert resp_sin_token.status_code == 401

def test_creacion_usuario_nuevo(client):
    """
    Prueba la creación de un nuevo usuario a través de /users/ (registro).
    Verifica que se devuelve la información correcta del usuario creado.
    """
    # Definir los datos para el nuevo usuario a registrar
    nuevo_usuario = {
        "cedula": "987654321",
        "name": "Ana Gomez",
        "email": "ana.gomez@example.com",
        "birth_date": "1985-05-15",
        "username": "anagomez",
        "password": "contrasegna",
        "rol": "Paciente",
        "ocupation": "Ingeniera"
    }

    # Llamar al endpoint de registro de usuario
    response = client.post("/users/", json=nuevo_usuario)
    assert response.status_code == 200
    resultado = response.json()

    # Verificar que la respuesta contiene los campos esperados
    assert "id" in resultado  # El nuevo usuario debería tener un ID asignado
    assert resultado["username"] == nuevo_usuario["username"]
    assert resultado["person"]["name"] == nuevo_usuario["name"]
    assert resultado["person"]["email"] == nuevo_usuario["email"]
    assert resultado["person"]["role"] == nuevo_usuario["rol"]  # El rol debería haberse asignado correctamente
    # La ocupación en la respuesta debe coincidir con la enviada
    assert resultado["ocupation"] == nuevo_usuario["ocupation"]

    # Comprobar que la contraseña almacenada está hasheada (no debe coincidir en texto plano)
    # Nota: La API almacena la contraseña con sha256, por lo que el valor devuelto no debe ser igual al original
    # (En la respuesta de creación, posiblemente no se devuelve la contraseña en absoluto por seguridad)
    assert "password" not in resultado  # La contraseña no debería aparecer en la respuesta

def test_audiometrias_creacion(client):
    usuario = {
        "cedula": "1122334455",
        "name": "Luis Martinez",
        "email": "luis.martinez@example.com",
        "birth_date": "1993-07-22",
        "username": "luismtz",
        "password": "pass1234",
        "rol": "Paciente",
        "ocupation": "Empleado"
    }

    # Crear usuario
    resp_usuario = client.post("/users/", json=usuario)
    assert resp_usuario.status_code == 200
    user_data = resp_usuario.json()
    user_id = user_data["id"]

    # Autenticación
    resp_login = client.post("/users/auth", json={
        "username": usuario["username"],
        "password": usuario["password"]
    })
    assert resp_login.status_code == 200
    token = resp_login.json()["access_token"]
    headers = {"Authorization": f"Bearer {token}"}

    # Crear audiometría
    audiometria_nueva = {
        "user_id": user_id,
        "decibel_frequencies": [
            {"decibel": 20.0, "frequency": 500.0, "ear": 0, "is_ear": True},
            {"decibel": 30.0, "frequency": 1000.0, "ear": 1, "is_ear": False}
        ]
    }

    resp_crea = client.post("/audiometries/", json=audiometria_nueva, headers=headers)
    assert resp_crea.status_code == 200
    resultado_crea = resp_crea.json()
    assert resultado_crea["user_id"] == user_id
    assert isinstance(resultado_crea["decibel_frequencies"], list)
    assert len(resultado_crea["decibel_frequencies"]) == 2

def test_audiometrias_consulta(client):
    # Primero, crear usuario y audiometría como en el test anterior
    usuario = {
        "cedula": "2233445566",
        "name": "Maria Lopez",
        "email": "maria.lopez@example.com",
        "birth_date": "1992-03-12",
        "username": "marialpz",
        "password": "pass5678",
        "rol": "Paciente",
        "ocupation": "Doctora"
    }

    resp_usuario = client.post("/users/", json=usuario)
    assert resp_usuario.status_code == 200
    user_data = resp_usuario.json()
    user_id = user_data["id"]

    resp_login = client.post("/users/auth", json={
        "username": usuario["username"],
        "password": usuario["password"]
    })
    assert resp_login.status_code == 200
    token = resp_login.json()["access_token"]
    headers = {"Authorization": f"Bearer {token}"}

    audiometria_nueva = {
        "user_id": user_id,
        "decibel_frequencies": [
            {"decibel": 25.0, "frequency": 750.0, "ear": 0, "is_ear": True},
            {"decibel": 35.0, "frequency": 1500.0, "ear": 1, "is_ear": False}
        ]
    }
    resp_crea = client.post("/audiometries/", json=audiometria_nueva, headers=headers)
    assert resp_crea.status_code == 200

    # Consultar audiometrías
    resp_consulta = client.get("/audiometries/", headers=headers)
    assert resp_consulta.status_code == 200
    lista_audiometrias = resp_consulta.json()
    assert isinstance(lista_audiometrias, list)
    assert len(lista_audiometrias) >= 1

    audiometria_respuesta = lista_audiometrias[0]
    assert isinstance(audiometria_respuesta, list)

    valores_numericos = []
    for item in audiometria_respuesta:
        if isinstance(item, list):
            valores_numericos.extend(item)
        elif isinstance(item, (int, float)):
            valores_numericos.append(item)

    assert any(v in [25.0, 35.0, 750.0, 1500.0] for v in valores_numericos), \
        f"No se encontraron valores esperados en la respuesta: {valores_numericos}"
    


def test_get_frecuency_by_id_found(db_session):
    # Arrange
    frecuency = Frecuency(value=1000)
    db_session.add(frecuency)
    db_session.commit()
    controller = GetFrequencyControllerImp(db_session)
    # Act
    result = controller.get_frecuency_by_id(frecuency.id)
    # Assert
    assert result.id == frecuency.id
    assert result.value == 1000

def test_get_frecuency_by_id_not_found(db_session):
    controller = GetFrequencyControllerImp(db_session)
    with pytest.raises(HTTPException) as excinfo:
        controller.get_frecuency_by_id(999)
    assert excinfo.value.status_code == 404
    assert "Frecuency not found" in excinfo.value.detail

def test_get_frequency_existing(db_session):
    frecuency = Frecuency(value=2000)
    db_session.add(frecuency)
    db_session.commit()
    controller = GetFrequencyControllerImp(db_session)
    result = controller.get_frequency(2000)
    assert result.value == 2000
    assert result.id == frecuency.id

def test_get_frequency_creates_new(db_session):
    controller = GetFrequencyControllerImp(db_session)
    result = controller.get_frequency(3000)
    assert result.value == 3000
    # Should now exist in DB
    db_obj = db_session.query(Frecuency).filter(Frecuency.value == 3000).first()
    assert db_obj is not None
    assert db_obj.value == 3000

def test_get_decibel_by_id_found(db_session):
    decibel = Decibel(value=55.5)
    db_session.add(decibel)
    db_session.commit()
    controller = GetDecibelControllerImp(db_session)
    result = controller.get_decibel_by_id(decibel.id)
    assert result.id == decibel.id
    assert result.value == 55.5
def test_get_decibel_by_id_not_found(db_session):
    controller = GetDecibelControllerImp(db_session)
    with pytest.raises(HTTPException) as excinfo:
        controller.get_decibel_by_id(9999)
    assert excinfo.value.status_code == 404
    assert "Decibel not found" in excinfo.value.detail
def test_get_decibel_existing(db_session):
    decibel = Decibel(value=70.0)
    db_session.add(decibel)
    db_session.commit()
    controller = GetDecibelControllerImp(db_session)
    result = controller.get_decibel(70.0)
    assert result.value == 70.0
    assert result.id == decibel.id
def test_get_decibel_creates_new(db_session):
    controller = GetDecibelControllerImp(db_session)
    result = controller.get_decibel(80.0)
    assert result.value == 80.0
    db_obj = db_session.query(Decibel).filter(Decibel.value == 80.0).first()
    assert db_obj is not None
    assert db_obj.value == 80.0

def test_get_ear_level_100(db_session):
    # Arrange
    controller = GetEarLeverImp(db_session)
    audiometry_data = Audiometry(
        user_id=1,
        decibel_frequencies=[
            DecibelFrequency(decibel=20, frequency=500, ear=True, is_ear=True),
            DecibelFrequency(decibel=30, frequency=1000, ear=False, is_ear=True)
        ]
    )
    
    # Act
    result = controller.get_ear_level(audiometry_data, 25)
    
    # Assert
    assert result == 100.0  # Perfecto estado auditivo en este caso


def test_get_ear_level_no_100(db_session):
    # Arrange
    controller = GetEarLeverImp(db_session)
    audiometry_data = Audiometry(
        user_id=1,
        decibel_frequencies=[
            DecibelFrequency(decibel=20, frequency=500, ear=True, is_ear=False),
            DecibelFrequency(decibel=30, frequency=1000, ear=False, is_ear=True)
        ]
    )
    
    # Act
    result = controller.get_ear_level(audiometry_data, 25)
    
    # Assert
    assert result < 100.0 