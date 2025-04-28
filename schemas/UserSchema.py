from pydantic import BaseModel
from datetime import date

# Nuevo esquema para la persona relacionada
class PersonData(BaseModel):
    name: str = None
    email: str = None
    role: str = None
    birth_date: date = None
    class Config:
        from_attributes = True


# Esquema de creación de usuario
from pydantic import BaseModel
from datetime import date

class UserCreate(BaseModel):
    name: str
    email: str
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

