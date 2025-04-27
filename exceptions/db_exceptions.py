from fastapi import HTTPException
from sqlalchemy.exc import IntegrityError

def handle_integrity_error(error: IntegrityError):
    if hasattr(error.orig, 'args') and error.orig.args[0] == 1062:
        # Error de duplicado en MySQL
        raise HTTPException(
            status_code=400,
            detail="Ya existe un registro con este valor único (probablemente email o username)."
        )
    else:
        raise HTTPException(
            status_code=400,
            detail="Error de integridad en la base de datos."
        )