package nl.craftsmen.brewery.company.controller;

import io.opentelemetry.instrumentation.annotations.SpanAttribute;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.craftsmen.brewery.company.model.Company;
import nl.craftsmen.brewery.company.model.Developer;

import java.util.List;
import java.util.Optional;

import static jakarta.ws.rs.core.Response.Status.CREATED;
import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;

@Path("/")
public class CompanyController {

    @Inject
    CompanyService companyService;

    @GET
    @Path("/companies")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCompanies() {
        List<Company> companies = companyService.findAll();
        if (companies.isEmpty()) {
            return Response.status(NOT_FOUND).build();
        }
        return Response.ok(companies).build();
    }

    @GET
    @Path("/company/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCompany(@PathParam("name") String name) {
        Optional<Company> company = companyService.findCompanyByName(name);
        return company.map(c -> Response.ok(c).build())
                .orElse(Response.status(NOT_FOUND).build());
    }

    @POST
    @Path("/company/new")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response newCompany(Company company) {
        Optional<Company> companyByName = companyService.findCompanyByName(company.getName());
        if (companyByName.isEmpty()) {
            return Response.status(CREATED).entity(companyService.saveCompany(company)).build();
        }
        return Response.noContent().build();
    }

    @DELETE
    @Path("/company/{companyId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCompany(@PathParam("companyId") Integer companyId) {
        Optional<Company> company = this.companyService.findCompanyById(companyId);
        return company.map(c -> {
            this.companyService.deleteCompany(c);
            return Response.noContent().build();
        }).orElse(Response.status(NOT_FOUND).build());
    }

    @PUT
    @Path("/company/{companyId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCompany(@PathParam("companyId") Integer companyId, Company company) {
        Company updatedCompany = this.companyService.updateCompany(companyId, company);
        if (updatedCompany != null) {
            return Response.ok(updatedCompany).build();
        } else {
            return Response.status(NOT_FOUND).build();
        }
    }

    @GET
    @Path("/company/{companyId}/developer/{developerId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCompanyDeveloper(@PathParam("companyId") Integer companyId, @PathParam("developerId") Integer developerId) {
        Optional<Company> company = companyService.findCompanyById(companyId);
        return company.map(c ->
                c.getDevelopers().stream().filter(developer -> developerId.equals(developer.getId()))
                        .findFirst().map(developer -> Response.ok(developer).build()).orElse(Response.status(NOT_FOUND).build())
        ).orElse(Response.status(NOT_FOUND).build());
    }

    @GET
    @Path("/developer/{lastname}/{firstname}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @WithSpan("find developer by name")  // Exercise 7 - Create a remote span
    // Exercise 8 - Add attributes to the spans
    public Response getDeveloperByName(@SpanAttribute("lastname") @PathParam("lastname") String lastname, @SpanAttribute("firstname") @PathParam("firstname") String firstname) {
        Optional<Developer> developer = companyService.findDeveloperByName(lastname, firstname);
        return developer.map(d -> Response.ok(d).build()).orElse(Response.status(NOT_FOUND).build());
    }
}
