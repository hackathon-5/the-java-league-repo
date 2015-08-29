package HolyGhost::Sighting;
use Mojo::Base 'Mojolicious::Controller';
use TryCatch;

sub get {
   my $self = shift;
   my $id = $self->param('id');

   my $sighting = $self->db->resultset('Sighting')->single({id => $id});
   if ($sighting) {
      my %cols = $sighting->get_columns;
      $self->render(json => \%cols);
   } else {
      $self->not_found;
   }
}

sub get_all {
   my $self = shift;
   my @sightings;

   foreach my $row ($self->db->resultset('Sighting')->all) {
      my %cols = $row->get_columns;
      push @sightings, \%cols;
   }

   $self->render(json => \@sightings);
}

sub create {
   my $self = shift;
   my $db = $self->db;

   my $sighting = $self->req->json;

   $sighting->{account_id} = $self->session->{user_id};
   delete $sighting->{seen_date};

   try {
      my %saved_sighting = $db->resultset('Sighting')->create($sighting)->get_columns;
      $self->render(json => \%saved_sighting, status => 201);
   } catch (DBIx::Error $err) {
      my $errstr = $err->{errstr};
      $self->log_error("db fault: $errstr}");
      $self->render(json => {error => $errstr}, status => 400);
   }
}

1;
