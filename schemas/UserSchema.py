from pydantic import BaseModel , EmailStr, Field
from datetime import date
class AuthUser(BaseModel):
    username: str
    password: str
    class Config:
        from_attributes = True

# Nuevo esquema para la persona relacionada
class PersonData(BaseModel):
    name: str = None
    email: EmailStr = None
    role: str = None
    birth_date: date = None
    class Config:
        from_attributes = True


# Esquema de creación de usuario
from pydantic import BaseModel
from datetime import date

class UserCreate(BaseModel):
    caducidad: int = None
    name: str
    email: EmailStr
    birth_date: date
    username: str
    password: str
    rol: str = None # El nombre del rol que se asignará al usuario
    ocupation: str = None  # Ocupación del usuario (puede ser el rol o cualquier otra cosa)
    class Config:
        orm_mode = True


# Esquema de salida de usuario
class UserOut(BaseModel):
    id: int
    username: str
    ocupation: str = None   
    person: PersonData 
    class Config:
        from_attributes = True

