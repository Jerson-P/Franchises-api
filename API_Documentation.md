# API Documentation for Franchise Management

## Base URL
Si está en local, la URL base es: http://localhost:8080


## Peticiones

### 1. Agregar una nueva franquicia
- **Tipo:** POST
- **URL:** `/franchises`
- **JSON:**
    ```json
    {
      "name": "Franquicia",
      "branches": []
    }
    ```

### 2. Añadir una nueva sucursal a una franquicia existente
- **Tipo:** POST
- **URL:** `/franchises/{franchiseId}/branches`
- **JSON:**
    ```json
    {
      "name": "Sucursal",
      "products": []
    }
    ```

### 3. Añadir un producto nuevo a la lista de productos de una sucursal
- **Tipo:** POST
- **URL:** `/franchises/{franchiseId}/branches/{branchId}/products`
- **JSON:**
    ```json
    {
      "name": "Producto",
      "stock": 50
    }
    ```

### 4. Eliminar un producto específico de una sucursal
- **Tipo:** DELETE
- **URL:** `/franchises/{franchiseId}/branches/{branchId}/products/{productId}`

### 5. Actualizar el stock de un producto específico
- **Tipo:** PUT
- **URL:** `/franchises/{franchiseId}/branches/{branchId}/products/{productId}/stock`
- **JSON:**
    ```json
    {
      "stock": 100
    }
    ```

### 6. Devolver el producto que tiene mayor stock en una sucursal específica
- **Tipo:** GET
- **URL:** `/franchises/{franchiseId}/branches/{branchId}/products/max-stock`

### 7. Cambiar el nombre de una franquicia
- **Tipo:** PUT
- **URL:** `/franchises/{franchiseId}/name`
- **JSON:**
    ```json
    {
      "name": "Franquicia nueva"
    }
    ```

### 8. Cambiar el nombre de una sucursal específica
- **Tipo:** PUT
- **URL:** `/franchises/{franchiseId}/branches/{branchId}/name`
- **JSON:**
    ```json
    {
      "name": "Sucursal nueva"
    }
    ```

### 9. Cambiar el nombre de un producto específico
- **Tipo:** PUT
- **URL:** `/franchises/{franchiseId}/branches/{branchId}/products/{productId}/name`
- **JSON:**
    ```json
    {
      "name": "Producto nueva"
    }
    ```

## Notas
- Asegúrate de reemplazar `{franchiseId}`, `{branchId}`, y `{productId}` con los valores correspondientes al realizar las solicitudes.
- Para las peticiones que requieren un cuerpo JSON, asegúrate de enviar el contenido correcto para evitar errores de validación.
