const TEAM = [
{ id: "p1", name: "María López", role: "QA Engineer", email: "maria.lopez@empresa.com" },
{ id: "p2", name: "Juan Pérez", role: "Frontend Dev", email: "juan.perez@empresa.com" },
{ id: "p3", name: "Lucía García", role: "Product Manager", email: "lucia.garcia@empresa.com" },
];

function Table() {
  const handleContact = (member) => {
    alert(`Contactando a ${member.name} en: ${member.email}`)
    // Aquí podrías abrir un modal, enviar email, etc.
  }

  return (
<div>


    <table>
        <thead>
            <tr>
                <th>Info</th>
                {TEAM.map(member => (
                    <th key={member.id}>{member.name}</th>
                ))}
            </tr>
        </thead>
        <tbody>
            <tr>
                <td><strong>Rol</strong></td>
                {TEAM.map(member => (
                    <td key={member.id}>{member.role}</td>
                ))}
            </tr>
            <tr>
                <td><strong>Contacto</strong></td>
                {TEAM.map(member => (
                    <td key={member.id}>
                        <button onClick={() => handleContact(member)}>
                            Contactar
                        </button>
                    </td>
                ))}
            </tr>
        </tbody>
    </table>
</div>
    )
}
export default Table