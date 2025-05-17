from sqlalchemy import create_engine
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker
from sqlalchemy.exc import SQLAlchemyError

DATABASE_URL = "mysql+pymysql://root:P4ng0l1n854@181.79.5.75:33306/audicheck"

# Crear el motor de conexión
engine = create_engine(DATABASE_URL, echo=True)

SessionLocal = sessionmaker(autoflush=False, autocommit=False, bind=engine)

Base = declarative_base()

try:
    # Intentamos establecer una conexión
    with engine.connect() as connection:
        print("Conexión exitosa a la base de datos.")
except SQLAlchemyError as e:
    print(f"Error al conectar a la base de datos: {e}")
