from database.Connections import engine, Base
from models import *
from models import User

Base.metadata.create_all(bind=engine)

print("Las tablas han sido creadas correctamente en la base de datos.")
