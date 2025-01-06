import { useEffect, useState } from "react";
import "../AdminPanel.css";
import { Extra } from "./Extra";
import "./Extras.css";
import { addExtraApi, deleteExtraApi, getAllExtrasApi } from "../../../Api/Api";

const Extras = () => {
  const [currentExtra, setCurrentExtra] = useState<Extra>(
    new Extra({ name: "", pricePerDay: 0.0 })
  );

  const [extras, setExtras] = useState<Extra[]>([]);
  const [error, setError] = useState("");

  const getExtras = async () => {
    const response = await getAllExtrasApi();
    if (response.data) {
      setExtras(response.data);
      console.log(response.data);
    }
  };

  useEffect(() => {
    getExtras();
  }, []);

  const onChangeName = (e: React.ChangeEvent<HTMLInputElement>) => {
    setCurrentExtra((prev) => {
      return new Extra({
        pricePerDay: prev.pricePerDay,
        name: e.target.value, // Replace the name field with the new value
      });
    });
  };

  const onChangePrice = (e: React.ChangeEvent<HTMLInputElement>) => {
    setCurrentExtra((prev) => {
      return new Extra({
        pricePerDay: Number(e.target.value),
        name: prev.name, // Replace the name field with the new value
      });
    });
  };

  const onClick = async () => {
    try {
      const response = await addExtraApi({
        name: currentExtra.name,
        pricePerDay: currentExtra.pricePerDay,
      });

      // If there's an error message from the API, set it in the error state
      if (response.message) {
        setError(response.message);
        return;
      }

      // You can clear the error if the API call succeeds (optional)
      setError("");
      getExtras();
    } catch (error) {
      // In case of a network or server error, set a fallback error message
      setError("An error occurred while adding the extra.");
    }
  };

  const onRowClick = async (id: number | undefined) => {
    if (!id) return;
    try {
      const response = await deleteExtraApi(id);
      if (response.message) {
        setError(response.message);
        return;
      }

      // If the deletion is successful, update the state directly to remove the deleted row
      setExtras((prevExtras) => prevExtras.filter((extra) => extra.id !== id));

      // Clear any error message
      setError("");
    } catch (error) {
      // Handle any error that occurs during the delete API call
      setError("An error occurred while deleting the extra.");
    }
  };

  return (
    <div className="extras-container">
      <h1 className="admin-panel-h1">Extras</h1>
      <div className="add-extra">
        <div className="labels-container">
          <label className="admin-panel__label name-label">Name</label>
          <label className="admin-panel__label price-label">Price</label>
        </div>
        <input
          className="admin-panel__input name-input"
          type="text"
          onChange={onChangeName}
          value={currentExtra.name}
        />
        <input
          className="admin-panel__input price-input"
          type="number"
          onChange={onChangePrice}
        />
        <label>zł</label>
        <button
          type="button"
          className="conveniences-btn add-btn"
          onClick={() => onClick()}
        >
          Add
        </button>
      </div>

      <table className="extras-table hoverable">
        <thead>
          <tr>
            <th>Name</th>
            <th>Price per day</th>
          </tr>
        </thead>
        <tbody>
          {extras.map((element, index) => (
            <tr key={index}>
              <td data-label="Name" onClick={() => onRowClick(element.id)}>
                {element.name}
              </td>
              <td data-label="price-per-day">{element.pricePerDay}</td>
            </tr>
          ))}
        </tbody>
      </table>
      {error && <div className="admin-panel-error">{error}</div>}
    </div>
  );
};

export default Extras;
