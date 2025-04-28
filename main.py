import sys
import os
sys.path.append(os.path.abspath(os.path.join(os.path.dirname(__file__), 'Back-End-Audichek')))


from database.Conections import engine, Base
from models import *
from models import User

Base.metadata.create_all(bind=engine)

print("Las tablas han sido creadas correctamente en la base de datos.")
