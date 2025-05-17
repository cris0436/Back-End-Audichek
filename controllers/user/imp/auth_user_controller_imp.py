import jwt
import datetime
from sqlalchemy.orm import Session
from fastapi import HTTPException, Depends
from hashlib import sha256
from models.entities import User
from schemas.UserSchema import AuthUser
from models.persistence.DatabaseSession import DataBaseSession
from fastapi.security import OAuth2PasswordBearer

# Clave secreta para firmar los tokens (¡cámbiala en producción!)
SECRET_KEY = "MI_CLAVE_SECRETA"
ALGORITHM = "HS256"

# Crear dependencia para el esquema de autenticación
oauth2_scheme = OAuth2PasswordBearer(tokenUrl="auth/login")

db_session = DataBaseSession()

class AuthUserControllerImp:
    def __init__(self, db: Session):
        self.db = db

    def auth_user(self, user_data: AuthUser):
        print(user_data.username, user_data.password)
        user = self.db.query(User).filter(User.username == user_data.username).first()
        password_hashed = sha256(user_data.password.encode()).hexdigest()

        if user and user.password == password_hashed:
            # Generar token JWT
            token = jwt.encode(
                {
                    "sub": user.username,
                    "exp": datetime.datetime.utcnow() + datetime.timedelta(hours=24),
                },
                SECRET_KEY,
                algorithm=ALGORITHM,
            )

            return {"access_token": token, "token_type": "bearer"}
        
        raise HTTPException(status_code=401, detail="Credenciales inválidas")

    # Función para obtener el usuario autenticado desde el token
def get_current_user(token: str = Depends(oauth2_scheme), db: Session = Depends(db_session.get_db)):
        try:
            payload = jwt.decode(token, SECRET_KEY, algorithms=[ALGORITHM])
            username = payload.get("sub")
            if username is None:
                raise HTTPException(status_code=401, detail="Token inválido")
            
            user = db.query(User).filter(User.username == username).first()
            if user is None:
                raise HTTPException(status_code=401, detail="Usuario no encontrado")
            
            return user
        except jwt.ExpiredSignatureError:
            raise HTTPException(status_code=401, detail="Token expirado")
        except jwt.InvalidTokenError:
            raise HTTPException(status_code=401, detail="Token inválido")

# Función para obtener una instancia del controlador
def get_auth_controller(db: Session = Depends(db_session.get_db)):
    return AuthUserControllerImp(db)

"""
    def logout(self):
        return self.auth_user_service.logout()

    def register(self, user_data):
        return self.auth_user_service.register(user_data)"""