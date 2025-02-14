package bwp.hhn.backend.harmonyhomenetlogic.serviceTests;

import bwp.hhn.backend.harmonyhomenetlogic.configuration.exeptions.customErrors.*;
import bwp.hhn.backend.harmonyhomenetlogic.entity.mainTables.*;
import bwp.hhn.backend.harmonyhomenetlogic.repository.mainTables.*;
import bwp.hhn.backend.harmonyhomenetlogic.repository.sideTables.PossessionHistoryRepository;
import bwp.hhn.backend.harmonyhomenetlogic.service.adapters.SmsService;
import bwp.hhn.backend.harmonyhomenetlogic.service.implementation.ProblemReportServiceImp;
import bwp.hhn.backend.harmonyhomenetlogic.service.interfaces.MailService;
import bwp.hhn.backend.harmonyhomenetlogic.utils.enums.Category;
import bwp.hhn.backend.harmonyhomenetlogic.utils.enums.Notification;
import bwp.hhn.backend.harmonyhomenetlogic.utils.enums.ReportStatus;
import bwp.hhn.backend.harmonyhomenetlogic.utils.request.ProblemReportRequest;
import bwp.hhn.backend.harmonyhomenetlogic.utils.response.page.PageResponse;
import bwp.hhn.backend.harmonyhomenetlogic.utils.response.typesOfPage.ProblemReportResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProblemReportServiceTest {

    @Mock
    private ProblemReportRepository problemReportRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ApartmentsRepository apartmentsRepository;

    @Mock
    private PossessionHistoryRepository possessionHistoryRepository;

    @Mock
    private MailService mailService;

    @Mock
    private SmsService smsService;

    @InjectMocks
    private ProblemReportServiceImp problemReportService;

    private User user;
    private Apartment apartment;
    private ProblemReport problemReport;
    private ProblemReportRequest problemReportRequest;
    private UUID userId;
    private UUID apartmentId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        userId = UUID.randomUUID();
        apartmentId = UUID.randomUUID();

        user = User.builder()
                .uuidID(userId)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phoneNumber("+1234567890")
                .notificationTypes(new ArrayList<>())
                .build();

        apartment = Apartment.builder()
                .uuidID(apartmentId)
                .apartmentSignature("A101")
                .address("123 Main St")
                .build();

        problemReport = ProblemReport.builder()
                .id(1L)
                .note("Leaky faucet")
                .reportStatus(ReportStatus.OPEN)
                .category(Category.GENERAL)
                .user(user)
                .apartment(apartment)
                .build();

        problemReportRequest = ProblemReportRequest.builder()
                .userId(userId)
                .apartmentSignature("A101")
                .note("Leaky faucet")
                .reportStatus(ReportStatus.OPEN)
                .category(Category.GENERAL)
                .build();
    }

    @Test
    void testCreateProblemReport_Success() throws UserNotFoundException, ApartmentNotFoundException {
        // Given
        when(apartmentsRepository.findByApartmentSignature("A101")).thenReturn(Optional.of(apartment));
        when(possessionHistoryRepository.existsByUserUuidIDAndApartmentUuidID(userId, apartmentId)).thenReturn(true);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(problemReportRepository.save(any(ProblemReport.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(apartmentsRepository.save(any(Apartment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        ProblemReportResponse response = problemReportService.createProblemReport(problemReportRequest);

        // Then
        assertNotNull(response);
        assertEquals("Leaky faucet", response.note());
        assertEquals("John Doe", response.userName());
        assertEquals("123 Main St", response.apartmentAddress());

        verify(apartmentsRepository, times(1)).findByApartmentSignature("A101");
        verify(possessionHistoryRepository, times(1)).existsByUserUuidIDAndApartmentUuidID(userId, apartmentId);
        verify(userRepository, times(1)).findById(userId);
        verify(problemReportRepository, times(1)).save(any(ProblemReport.class));
        verify(userRepository, times(1)).save(any(User.class));
        verify(apartmentsRepository, times(1)).save(any(Apartment.class));
    }

    @Test
    void testCreateProblemReport_ApartmentNotFound() {
        // Given
        when(apartmentsRepository.findByApartmentSignature("A101")).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ApartmentNotFoundException.class, () -> problemReportService.createProblemReport(problemReportRequest));

        verify(apartmentsRepository, times(1)).findByApartmentSignature("A101");
        verifyNoInteractions(possessionHistoryRepository);
        verifyNoInteractions(userRepository);
        verifyNoInteractions(problemReportRepository);
    }

    @Test
    void testCreateProblemReport_UserNotAuthorized() {
        // Given
        when(apartmentsRepository.findByApartmentSignature("A101")).thenReturn(Optional.of(apartment));
        when(possessionHistoryRepository.existsByUserUuidIDAndApartmentUuidID(userId, apartmentId)).thenReturn(false);

        // When & Then
        assertThrows(UserNotFoundException.class, () -> problemReportService.createProblemReport(problemReportRequest));

        verify(apartmentsRepository, times(1)).findByApartmentSignature("A101");
        verify(possessionHistoryRepository, times(1)).existsByUserUuidIDAndApartmentUuidID(userId, apartmentId);
        verifyNoInteractions(userRepository);
        verifyNoInteractions(problemReportRepository);
    }

    @Test
    void testUpdateProblemReport_Success() throws ProblemReportNotFoundException {
        // Given
        ProblemReportRequest updateRequest = ProblemReportRequest.builder()
                .note("Fixed leaky faucet")
                .reportStatus(ReportStatus.DONE)
                .category(Category.TECHNICAL)
                .build();

        NotificationType emailNotification = NotificationType.builder()
                .type(Notification.EMAIL)
                .build();

        NotificationType smsNotification = NotificationType.builder()
                .type(Notification.SMS)
                .build();

        user.getNotificationTypes().add(emailNotification);
        user.getNotificationTypes().add(smsNotification);

        when(problemReportRepository.findById(1L)).thenReturn(Optional.of(problemReport));
        when(problemReportRepository.save(any(ProblemReport.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        ProblemReportResponse response = problemReportService.updateProblemReport(1L, updateRequest);

        // Then
        assertNotNull(response);
        assertEquals("Fixed leaky faucet", response.note());
        assertEquals(ReportStatus.DONE, response.reportStatus());
        assertEquals(Category.TECHNICAL, response.category());
        assertNotNull(response.endDate());

        verify(problemReportRepository, times(1)).findById(1L);
        verify(problemReportRepository, times(1)).save(any(ProblemReport.class));
        verify(mailService, times(1)).sendNotificationMail(eq("Problem report done"), eq("Your problem report has been done"), eq(user.getEmail()));
        verify(smsService, times(1)).sendSms(eq("Your problem report has been done"), eq(user.getPhoneNumber()));
    }

    @Test
    void testUpdateProblemReport_NotFound() {
        // Given
        ProblemReportRequest updateRequest = ProblemReportRequest.builder()
                .note("Fixed leaky faucet")
                .reportStatus(ReportStatus.DONE)
                .category(Category.GENERAL)
                .build();

        when(problemReportRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ProblemReportNotFoundException.class, () -> problemReportService.updateProblemReport(1L, updateRequest));

        verify(problemReportRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(problemReportRepository);
    }

    @Test
    void testDeleteProblemReport_Success() throws ProblemReportNotFoundException {
        // Given
        when(problemReportRepository.existsById(1L)).thenReturn(true);
        doNothing().when(problemReportRepository).deleteById(1L);

        // When
        String result = problemReportService.deleteProblemReport(1L);

        // Then
        assertEquals("Problem report deleted successfully", result);
        verify(problemReportRepository, times(1)).existsById(1L);
        verify(problemReportRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteProblemReport_NotFound() {
        // Given
        when(problemReportRepository.existsById(1L)).thenReturn(false);

        // When & Then
        assertThrows(ProblemReportNotFoundException.class, () -> problemReportService.deleteProblemReport(1L));

        verify(problemReportRepository, times(1)).existsById(1L);
        verifyNoMoreInteractions(problemReportRepository);
    }

    @Test
    void testGetProblemReportsByApartmentSignature_Success() throws ApartmentNotFoundException {
        // Given
        int pageNo = 0;
        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        List<ProblemReport> problemReports = Collections.singletonList(problemReport);
        Page<ProblemReport> problemReportPage = new PageImpl<>(problemReports, pageable, problemReports.size());

        when(apartmentsRepository.findByApartmentSignature("A101")).thenReturn(Optional.of(apartment));
        when(problemReportRepository.findAllByApartmentUuidID(apartmentId, pageable)).thenReturn(problemReportPage);

        // When
        PageResponse<ProblemReportResponse> responses = problemReportService.getProblemReportsByApartmentSignature("A101", pageNo, pageSize);

        // Then
        assertNotNull(responses);
        assertEquals(1, responses.content().size());
        assertEquals("Leaky faucet", responses.content().get(0).note());

        verify(apartmentsRepository, times(1)).findByApartmentSignature("A101");
        verify(problemReportRepository, times(1)).findAllByApartmentUuidID(apartmentId, pageable);
    }

    @Test
    void testGetProblemReportsByApartmentSignature_NotFound() {
        // Given
        int pageNo = 0;
        int pageSize = 10;

        when(apartmentsRepository.findByApartmentSignature("A101")).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ApartmentNotFoundException.class, () -> problemReportService.getProblemReportsByApartmentSignature("A101", pageNo, pageSize));

        verify(apartmentsRepository, times(1)).findByApartmentSignature("A101");
        verifyNoInteractions(problemReportRepository);
    }

    @Test
    void testGetAllProblemReports_Success() {
        // Given
        int pageNo = 0;
        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        List<ProblemReport> problemReports = Collections.singletonList(problemReport);
        Page<ProblemReport> problemReportPage = new PageImpl<>(problemReports, pageable, problemReports.size());

        when(problemReportRepository.findAll(pageable)).thenReturn(problemReportPage);

        // When
        PageResponse<ProblemReportResponse> responses = problemReportService.getAllProblemReports(pageNo, pageSize);

        // Then
        assertNotNull(responses);
        assertEquals(1, responses.content().size());
        assertEquals("Leaky faucet", responses.content().get(0).note());

        verify(problemReportRepository, times(1)).findAll(pageable);
    }
}
